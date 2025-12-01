package com.soami.mini_zwift_backend.routes;

import com.soami.mini_zwift_backend.routes.dao.JdbcRouteDao;
import com.soami.mini_zwift_backend.routes.model.Route;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase (replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JdbcRouteDao.class)
class JdbcRouteDaoTest {

    @Autowired
    private JdbcRouteDao jdbcRouteDao;

    @Test
    void create_and_findById_roundTrip() {
        // arrange
        String name = "Central Park Loop";
        Double distanceKm = 10.5;

        // act
        Route created = jdbcRouteDao.create(name, distanceKm);
        Optional<Route> found = jdbcRouteDao.findById(created.getId());

        // assert
        assertThat(found).isPresent();
        Route route = found.get();
        assertThat(route.getName()).isEqualTo(name);
        assertThat(route.getDistanceKm()).isEqualTo(distanceKm);
        assertThat(route.getId()).isNotNull();
        assertThat(route.getCreatedAt()).isNotNull();
        // when the test ends, the transaction rolls back -> no data persists
    }

    @Test
    void findByName_returnsRoute_whenExists() {
        // arrange
        Route created = jdbcRouteDao.create("Hudson Greenway", 15.2);

        // act
        Optional<Route> found = jdbcRouteDao.findByName("Hudson Greenway");

        // assert
        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(created.getId());
    }

    @Test
    void findByName_returnsEmpty_whenNotExists() {
        Optional<Route> found = jdbcRouteDao.findByName("Nonexistent Route");
        assertThat(found).isEmpty();
    }

    @Test
    void findAll_returnsAllRoutes() {
        // arrange
        jdbcRouteDao.create("Route A", 5.0);
        jdbcRouteDao.create("Route B", 8.0);

        // act
        List<Route> routes = jdbcRouteDao.findAll();

        // assert
        assertThat(routes)
                .extracting(Route::getName)
                .contains("Route A", "Route B");
    }

    @Test
    void delete_removesRoute() {
        // arrange
        Route created = jdbcRouteDao.create("Delete Me Route", 7.7);

        // act
        int rows = jdbcRouteDao.delete(created.getId());
        Optional<Route> afterDelete = jdbcRouteDao.findById(created.getId());

        // assert
        assertThat(rows).isEqualTo(1);
        assertThat(afterDelete).isEmpty();
    }

    @Test
    void findById_returnsEmpty_whenNotFound() {
        Optional<Route> result = jdbcRouteDao.findById(UUID.randomUUID());
        assertThat(result).isEmpty();
    }
}

