package finalproject.suppliersystem.supplier.controller;

import finalproject.suppliersystem.supplier.domain.*;
import finalproject.suppliersystem.supplier.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

@Controller
public class SupplierController {

    private final SupplierService supplierService;
    private final AddressService addressService;
    private final ContactInformationService contactInformationService;
    private final ContactPersonService contactPersonService;
    private final ProductCategoryService productCategoryService;

    public SupplierController(SupplierService supplierService,
                              AddressService addressService,
                              ContactInformationService contactInformationService,
                              ContactPersonService contactPersonService,
                              ProductCategoryService productCategoryService) {
        this.supplierService = supplierService;
        this.addressService = addressService;
        this.contactInformationService = contactInformationService;
        this.contactPersonService = contactPersonService;
        this.productCategoryService = productCategoryService;
    }

    /**
     * Al entities containing information about the supplier is sent to
     * registration/supplier-site within the model
     * @param supplier
     * @param contactInformation
     * @param address
     * @param contactPerson
     * @param model
     * @return registration/supplier-HTML
     */
    @GetMapping("/registration/supplier")
    public String showRegisterSupplier(Supplier supplier,
                                       ContactInformation contactInformation,
                                       Address address,
                                       ContactPerson contactPerson,
                                       Model model){
        model.addAttribute("supplier", supplier);
        model.addAttribute("contactInformation", contactInformation);
        model.addAttribute("address", address);
        model.addAttribute("contactPerson", contactPerson);
        //the user chooses one or several PorductCategories
        model.addAttribute("productCategory", productCategoryService.findAll());
        return "/registration/supplier";
    }

    /**
     * PostMapping receives data from HTML and Controller asks SupplierService to
     * validate with Valid/BindingResult.
     * Though, ProductCategories are not created in this connection, but the user
     * only chooses one og several of them. So they are not validated either.
     * @param supplier
     * @param contactInformation
     * @param address
     * @param contactPerson
     * @param productCategorySet
     * @param bindingResultSupplier
     * @param bindingResultContactInformation
     * @param bindingResultAddress
     * @param bindingResultContactPerson
     * @param model
     * @return
     */

    @PostMapping("/registration/supplier")
    public String registerSupplier(@Valid Supplier supplier,
                                   @Valid ContactInformation contactInformation,
                                   @Valid Address address,
                                   @Valid ContactPerson contactPerson,
                                   SortedSet<ProductCategory> productCategorySet,
                                   BindingResult bindingResultSupplier,
                                   BindingResult bindingResultContactInformation,
                                   BindingResult bindingResultAddress,
                                   BindingResult bindingResultContactPerson,
                                   Model model){

        /*
          alle forskellige bindingResults skal være på HTML-siden.
          Fordi vi henter productKategorier fra databasen,
          bliver navnene på kategorierne ikke valideret her.
          Men vi må oprette kategorierne i databasen for at sortedSet ikke er tom.
         */
        if(supplierService.hasErrors(bindingResultSupplier, bindingResultContactInformation,
                bindingResultAddress, bindingResultContactPerson)) {
            return "/registration/supplier";
        }

        //created skal sættes ind på HTML-siden
        if(supplierService.existAlready(supplier, address)) {
            String created = "Supplier is already registred with the supplier number: " + supplier.getSupplierNumber();
            model.addAttribute("created", created);
            return "/registration/supplier";
        }

        supplierService.save(supplier);
        contactInformationService.save(contactInformation);
        contactInformation.setSupplier(supplier);
        addressService.save(address);
        address.setContactInformation(contactInformation);
        contactPersonService.save(contactPerson);
        contactPerson.setContactInformation(contactInformation);
        /*
          hvordan kommer de valgte kategorier fra HTML til PostMapping?
          Der bliver sendt et sæt med alle og brugeren vælger et eller flere af dem.
          Bliver det rigtigt nu? SortedSet er med i PostMapping og knyttes her til supplier:
        */
        supplier.setProductCategorySet(productCategorySet);

        int supplierNumber= supplier.getSupplierNumber();
        return "redirect: /registration/supplier/" + supplierNumber;
    }

    //nu går dette til supplier confirmation-side,
    // men når vi har lavet forsiden, kan bekræftelsen sendes dertil
    @GetMapping("/registration/supplier/{supplierNumber}")
    public String confirmRegistration(@PathVariable("supplierNumber") int supplierNumber, Model model){
            model.addAttribute("confirmation", "SupplierNumber " + supplierNumber + " is registred.");
            return "/registration/supplier_confirmation";
    }

}


