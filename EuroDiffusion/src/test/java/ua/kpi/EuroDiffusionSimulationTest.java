package ua.kpi;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for EuroDiffusionSimulation
 * @author Andrii Severin
 */
public class EuroDiffusionSimulationTest
{
    @Test
    public void oneCountryTest() throws Exception {
        CountryCoinsDistribution Luxembourg =
                new CountryCoinsDistribution("Luxembourg", 1, 1, 1, 1);
        EuroDiffusionSimulation euroDiffusionSimulation2 =
                new EuroDiffusionSimulation(Luxembourg);
        euroDiffusionSimulation2.simulate();
        assertEquals(0, Luxembourg.getNumberOfDays());
    }

    @Test
    public void twoCountriesTest() throws Exception {
        CountryCoinsDistribution Netherlands =
                new CountryCoinsDistribution("Netherlands", 1, 3, 2, 4);
        CountryCoinsDistribution Belgium =
                new CountryCoinsDistribution("Belgium", 1, 1, 2, 2);
        EuroDiffusionSimulation euroDiffusionSimulation =
                new EuroDiffusionSimulation(Netherlands, Belgium);
        euroDiffusionSimulation.simulate();
        assertEquals(2, Netherlands.getNumberOfDays());
        assertEquals(2, Belgium.getNumberOfDays());
    }

    @Test
    public void threeCountriesTest() throws Exception {
        CountryCoinsDistribution France =
                new CountryCoinsDistribution("France", 1, 4, 4, 6);
        CountryCoinsDistribution Spain =
                new CountryCoinsDistribution("Spain", 3, 1, 6, 3);
        CountryCoinsDistribution Portugal =
                new CountryCoinsDistribution("Portugal", 1, 1, 2, 2);
        EuroDiffusionSimulation euroDiffusionSimulation1 =
                new EuroDiffusionSimulation(France, Spain, Portugal);
        euroDiffusionSimulation1.simulate();
        assertEquals(1325, France.getNumberOfDays());
        assertEquals(382, Spain.getNumberOfDays());
        assertEquals(416, Portugal.getNumberOfDays());
    }
}
