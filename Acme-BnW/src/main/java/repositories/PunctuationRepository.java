
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Punctuation;

@Repository
public interface PunctuationRepository extends JpaRepository<Punctuation, Integer> {

}
