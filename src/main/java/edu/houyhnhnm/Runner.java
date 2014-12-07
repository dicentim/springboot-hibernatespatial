package edu.houyhnhnm;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import edu.houyhnhnm.domain.Position;
import edu.houyhnhnm.reposiotry.PositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public final class Runner {

    private static final Logger LOG = LoggerFactory.getLogger(Runner.class);

    private static final WKTReader READER = new WKTReader();

    private Runner() throws ParseException {
        final ConfigurableApplicationContext context = SpringApplication.run(Application.class);
        final PositionRepository repository = context.getBean(PositionRepository.class);
        final Point location = buildGeometry(10, 5);
        repository.save(new Position("test", location));

        getAllPoints(repository);
    }

    private void getAllPoints(PositionRepository repository) {
        final Iterable<Position> all = repository.findAll();
        for (Position position : all) {
            LOG.info("Point " + position.toString());
        }
    }

    private Point buildGeometry(int x, int y) throws ParseException {
        final String format = String.format("POINT(%s %s)", x, y);
        final Geometry result = READER.read(format);
        // this is ot the most elegant way to build a point
        return (Point) result;
    }


    public static void main(String[] args) throws ParseException {
        new Runner();

    }



}
