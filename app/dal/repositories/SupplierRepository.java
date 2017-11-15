package dal.repositories;

import dal.interfaces.SupplierContext;
import models.storage.Supplier;

import java.util.List;

public class SupplierRepository implements SupplierContext{
    private SupplierContext context;

    public SupplierRepository(SupplierContext context) {
        this.context = context;
    }

    @Override
    public boolean addSupplier(Supplier supplier) {
        return context.addSupplier(supplier);
    }

    @Override
    public boolean updateSupplier(Supplier supplier) {
        return context.updateSupplier(supplier);
    }

    @Override
    public boolean removeSupplier(Supplier supplier) {
        return context.removeSupplier(supplier);
    }

    @Override
    public Supplier getSupplier(String name) {
        return context.getSupplier(name);
    }

    @Override
    public List<Supplier> getAll() {
        return context.getAll();
    }
}
