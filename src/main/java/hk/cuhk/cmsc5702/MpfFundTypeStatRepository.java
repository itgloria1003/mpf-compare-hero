package hk.cuhk.cmsc5702;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MpfFundTypeStatRepository extends PagingAndSortingRepository<MpfFundTypeStat, Long> {

    public Iterable<MpfFundTypeStat> findByAsOfDate(String asOfDate);
}
