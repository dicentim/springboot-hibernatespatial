/*
 * Created by Donatello Dicentim on 8/9/14.
 */

package edu.houyhnhnm.reposiotry;

import edu.houyhnhnm.domain.Position;
import org.springframework.data.repository.CrudRepository;

/**
 * @author: Donatello Dicentim
 */
public interface PositionRepository extends CrudRepository<Position, Long> {
}
