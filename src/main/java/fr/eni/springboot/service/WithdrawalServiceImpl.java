package fr.eni.springboot.service;

import fr.eni.springboot.bo.Withdrawal;
import fr.eni.springboot.repository.WithdrawalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WithdrawalServiceImpl implements WithdrawalService{

    WithdrawalRepository dao;

    public WithdrawalServiceImpl(WithdrawalRepository dao) {
        this.dao = dao;
    }

    @Override
    public void createWithdrawal(Withdrawal withdrawal) {
        dao.createWithdrawal(withdrawal);
    }

    @Override
    public List<Withdrawal> readWithdrawal() {
        return dao.readWithdrawal();
    }

    @Override
    public void updateWithdrawal(Withdrawal withdrawal) {
        dao.updateWithdrawal(withdrawal);
    }

    @Override
    public void deleteWithdrawal(long id) {
        dao.deleteWithdrawal(id);
    }
}
