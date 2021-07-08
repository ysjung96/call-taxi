package flowerdelivery;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="items", path="items")
public interface TaxiRepository extends PagingAndSortingRepository<Taxi, Long>{


}
