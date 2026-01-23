package fr.eni.springboot.repository;

import fr.eni.springboot.bo.Withdrawal;

import java.util.List;

public interface WithdrawalRepository {
    //CRUD
    void createWithdrawal(Withdrawal withdrawal);

    List<Withdrawal> readWithdrawal();

    void updateWithdrawal(Withdrawal withdrawal);

    void deleteWithdrawal(long id);
}
