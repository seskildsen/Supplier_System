package finalproject.suppliersystem.supplier.restapi.restcontroller;


import finalproject.suppliersystem.core.enums.CorporateSocialResponsibility;
import finalproject.suppliersystem.supplier.restapi.service.ICalculatorSupplierRiskLevelRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @RestController is a specialized version of the Controller. It's a REST style controller
 * Handles methods that shall return JSON/XML response directly to the client
 * Has @Controller and @ResponseBody
 * https://howtodoinjava.com/spring-boot2/rest/controller-restcontroller/
 */
@RestController
public class CalculatorSupplierRiskLevelRestController
{
    private final ICalculatorSupplierRiskLevelRestService iCalculatorSupplierRiskLevelRestService;

    /**
     * Constructor injection
     * We do not have to specify @Autowired, as long as the class only have one constructor and the class itself
     * is annotated with a Spring bean, because Spring automatic make the dependency to be injected via the constructor.
     * It is used here just for readability
     *
     * To understand how constructor injection works:
     * https://stackoverflow.com/questions/40620000/spring-autowire-on-properties-vs-constructor
     * https://reflectoring.io/constructor-injection/
     *
     * @param iCalculatorSupplierRiskLevelRestService - interface of provided method
     */
    @Autowired
    public CalculatorSupplierRiskLevelRestController(ICalculatorSupplierRiskLevelRestService iCalculatorSupplierRiskLevelRestService)
    {
        this.iCalculatorSupplierRiskLevelRestService = iCalculatorSupplierRiskLevelRestService;
    }

    /**
     * Mono<ResponseEntity<T>> provides response status, headers, and body asynchronously
     * https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#webflux-ann-responseentity
     */
    @GetMapping(value = "/supplier_risk_level_api", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> getSupplierRiskLevel(CorporateSocialResponsibility corporateSocialResponsibility, int issuesConcerningCooperation, int availabilityIssues)
    {

        return iCalculatorSupplierRiskLevelRestService.calculateSupplierRiskLevel(corporateSocialResponsibility, issuesConcerningCooperation, availabilityIssues);
    }
}