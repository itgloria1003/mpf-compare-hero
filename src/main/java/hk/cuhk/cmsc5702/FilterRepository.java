package hk.cuhk.cmsc5702;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface FilterRepository extends PagingAndSortingRepository<FilterParam, String> {

    public Iterable<FilterParam> findAllByFilterType(String filterType);
    public Iterable<FilterParam> findAllByFilterTypeAndCode(String filterType, String code);
}
