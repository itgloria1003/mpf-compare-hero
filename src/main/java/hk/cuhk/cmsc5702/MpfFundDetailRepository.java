package hk.cuhk.cmsc5702;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MpfFundDetailRepository extends PagingAndSortingRepository<MpfFundDetail, Long> {

	Iterable<MpfFundDetail> findByFundTypeAndTrustee(String filterFundType, String filterTrustee);

	Iterable<MpfFundDetail> findByFundType(String filterFundType);

	Iterable<MpfFundDetail> findByTrustee(String filterTrustee);


	Iterable<MpfFundDetail> findByOrderBySchemeAscConstituentFundAsc();

	Iterable<MpfFundDetail> findByTrusteeOrderBySchemeAscConstituentFundAsc(String filterTrustee);

	Iterable<MpfFundDetail> findByFundTypeOrderBySchemeAscConstituentFundAsc(String filterFundType);

	Iterable<MpfFundDetail> findByFundTypeAndTrusteeOrderBySchemeAscConstituentFundAsc(String filterFundType,
			String filterTrustee);



    
}
