package finalproject.suppliersystem.supplier.criticalityview.service;

import finalproject.suppliersystem.supplier.criticalityview.view.SupplierCriticalityView;

import java.util.List;

public interface ISupplierCriticalityViewService
{
    SupplierCriticalityView findById(Long id);

    List<SupplierCriticalityView> findAll();
}
