package com.soami.mini_zwift_backend.routes;

import com.soami.mini_zwift_backend.routes.dao.RouteDao;
import com.soami.mini_zwift_backend.routes.exceptions.InvalidRouteDataException;
import com.soami.mini_zwift_backend.routes.exceptions.RouteNotFoundException;
import com.soami.mini_zwift_backend.routes.model.Route;
import com.soami.mini_zwift_backend.routes.service.RouteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class RouteServiceTest {

    private final RouteDao routeDao = Mockito.mock(RouteDao.class);
    private final RouteService routeService = new RouteService(routeDao);

    @Test
    void createRoute_validData_callsDaoAndReturnsRoute() {
        String name = "Central Park Loop";
        Double distanceKm = 10.5;

        Route fake = Route.builder()
                .id(UUID.randomUUID())
                .name(name)
                .distanceKm(distanceKm)
                .createdAt(Instant.now())
                .build();

        when(routeDao.create(name, distanceKm)).thenReturn(fake);

        Route result = routeService.createRoute(name, distanceKm);

        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getDistanceKm()).isEqualTo(distanceKm);
        verify(routeDao).create(name, distanceKm);
        verifyNoMoreInteractions(routeDao);
    }

    @Test
    void createRoute_blankName_throwsInvalidRouteDataException() {
        assertThatThrownBy(() -> routeService.createRoute("   ", 10.0))
                .isInstanceOf(InvalidRouteDataException.class)
                .hasMessageContaining("Route name must not be BLANK");

        verifyNoInteractions(routeDao);
    }

    @Test
    void createRoute_nonPositiveDistance_throwsInvalidRouteDataException() {
        assertThatThrownBy(() -> routeService.createRoute("Valid Name", 0.0))
                .isInstanceOf(InvalidRouteDataException.class)
                .hasMessageContaining("Route distance must be greater than zero");

        verifyNoInteractions(routeDao);
    }

    @Test
    void getRouteById_found_returnsRoute() {
        UUID id = UUID.randomUUID();
        Route fake = Route.builder()
                .id(id)
                .name("Hudson Greenway")
                .distanceKm(15.2)
                .createdAt(Instant.now())
                .build();

        when(routeDao.findById(id)).thenReturn(Optional.of(fake));

        Route result = routeService.getRouteById(id);

        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getName()).isEqualTo("Hudson Greenway");
        verify(routeDao).findById(id);
        verifyNoMoreInteractions(routeDao);
    }

    @Test
    void getRouteById_notFound_throwsRouteNotFoundException() {
        UUID id = UUID.randomUUID();
        when(routeDao.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> routeService.getRouteById(id))
                .isInstanceOf(RouteNotFoundException.class)
                .hasMessageContaining("Route not found");

        verify(routeDao).findById(id);
        verifyNoMoreInteractions(routeDao);
    }

    @Test
    void getAllRoutes_delegatesToDao() {
        when(routeDao.findAll()).thenReturn(List.of(
                Route.builder().id(UUID.randomUUID()).name("A").distanceKm(5.0).createdAt(Instant.now()).build(),
                Route.builder().id(UUID.randomUUID()).name("B").distanceKm(8.0).createdAt(Instant.now()).build()
        ));

        List<Route> routes = routeService.getAllRoutes();

        assertThat(routes).hasSize(2);
        verify(routeDao).findAll();
        verifyNoMoreInteractions(routeDao);
    }
}

