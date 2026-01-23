package fr.eni.springboot.service;

import fr.eni.springboot.bo.Withdrawal;

import java.util.List;

public interface WithdrawalService {
    void createWithdrawal(Withdrawal withdrawal);

    List<Withdrawal> readWithdrawal();

    void updateWithdrawal(Withdrawal withdrawal);

    void deleteWithdrawal(long id);
}
