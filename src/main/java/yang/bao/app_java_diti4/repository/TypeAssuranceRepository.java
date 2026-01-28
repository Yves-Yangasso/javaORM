package yang.bao.app_java_diti4.repository;

import yang.bao.app_java_diti4.Entity.TypeAssurance;

import yang.bao.app_java_diti4.repository.interfaceRepo.IInterface;

import java.util.List;

public class TypeAssuranceRepository  implements IInterface<TypeAssurance> {
    @Override
    public void insert(TypeAssurance typeAssurance) {

    }

    @Override
    public void update(TypeAssurance typeAssurance) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public TypeAssurance findById(int id) {
        return null;
    }

    @Override
    public List<TypeAssurance> findAll() {
        return List.of();
    }
}