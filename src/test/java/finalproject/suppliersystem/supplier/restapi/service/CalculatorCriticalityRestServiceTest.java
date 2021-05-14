package finalproject.suppliersystem.supplier.restapi.service;

import finalproject.suppliersystem.core.enums.CategoryLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


public class CalculatorCriticalityRestServiceTest {

    private CalculatorCriticalityRestService calculatorCriticalityRestService;

    @BeforeEach
    void setUp()
    {
        calculatorCriticalityRestService = new CalculatorCriticalityRestService();
    }

    /**
     * https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-sources-MethodSource
     * Read explanation in CalculatorSupplierRiskelLevelRestServiceTest
     */

    @ParameterizedTest
    @MethodSource("argumentProviderHigh")
    void calculateCriticalityHighTest(CategoryLevel supplierRiskLevel, Long volume){
        assertEquals(CategoryLevel.HIGH, calculatorCriticalityRestService.calculateCriticality(supplierRiskLevel, volume));
    }

    @ParameterizedTest
    @MethodSource("argumentProviderMedium")
    void calculateCriticalityMediumTest(CategoryLevel supplierRiskLevel, Long volume){
        assertEquals(CategoryLevel.MEDIUM, calculatorCriticalityRestService.calculateCriticality(supplierRiskLevel, volume));
    }

    @ParameterizedTest
    @MethodSource("argumentProviderLow")
    void calculateCriticalityLowTest(CategoryLevel supplierRiskLevel, Long volume){
        assertEquals(CategoryLevel.LOW, calculatorCriticalityRestService.calculateCriticality(supplierRiskLevel, volume));
    }

    /*
        factory methods, that provides arguments to calculateCriticalityTest

          volumeLow volume >= 0 && volume <= 10000;
          volume > 10000 && volume <= 30000;
          volumeHigh > 30000;
     */

    private static Stream<Arguments> argumentProviderHigh()
    {
        return Stream.of(
                Arguments.arguments(CategoryLevel.HIGH, 30001L),
                Arguments.arguments(CategoryLevel.HIGH, 20000L),
                Arguments.arguments(CategoryLevel.HIGH, 10000L),
                Arguments.arguments(CategoryLevel.MEDIUM, 30001L)
        );
    }

    private static Stream<Arguments> argumentProviderMedium()
    {
        return Stream.of(
                Arguments.arguments(CategoryLevel.MEDIUM, 20000L),
                Arguments.arguments(CategoryLevel.MEDIUM, 10000L),
                Arguments.arguments(CategoryLevel.LOW, 30001L),
                Arguments.arguments(CategoryLevel.LOW, 20000L)
        );
    }

    private static Stream<Arguments> argumentProviderLow()
    {
        return Stream.of(
                Arguments.arguments(CategoryLevel.LOW, 10000L)
        );
    }

}
