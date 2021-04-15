package bo.ucb.edu.vic19.dao;

import bo.ucb.edu.vic19.model.Transaction;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionDao {
    public Integer getLastInsertId();
    public Integer create(Transaction transaction);
}
