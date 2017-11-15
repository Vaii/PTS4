package dal.interfaces;

import models.storage.Supplier;

import java.util.List;

public interface SupplierContext {
    boolean addSupplier(Supplier supplier);
    boolean updateSupplier(Supplier supplier);
    boolean removeSupplier(Supplier supplier);
    Supplier getSupplier(String name);
    List<Supplier> getAll();
}
