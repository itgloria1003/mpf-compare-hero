package hk.cuhk.cmsc5702;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface MpfFundDetailRepository extends PagingAndSortingRepository<MpfFundDetail, Long>,QueryByExampleExecutor<MpfFundDetail>  {

	Iterable<MpfFundDetail> findByFundTypeAndTrustee(String filterFundType, String filterTrustee);

	Iterable<MpfFundDetail> findByFundType(String filterFundType);

	Iterable<MpfFundDetail> findByTrustee(String filterTrustee);

//
	Iterable<MpfFundDetail> findByOrderBySchemeAscConstituentFundAsc();

//	Iterable<MpfFundDetail> findByTrusteeOrderBySchemeAscConstituentFundAsc(String filterTrustee);
//
//	Iterable<MpfFundDetail> findByFundTypeOrderBySchemeAscConstituentFundAsc(String filterFundType);

//	Iterable<MpfFundDetail> findByFundTypeAndTrusteeOrderBySchemeAscConstituentFundAsc(String filterFundType,
//			String filterTrustee);

	Iterable<MpfFundDetail> findByLatestFERIsNotNull();

	Iterable<MpfFundDetail>  findByFundRiskIndicatorNotNull();


	

	
    
}
