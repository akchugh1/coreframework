@E2E_TEST
@BACKEND_TEST
Feature: Implementation of all backend tasks

  @BACKEND_TASK1
  Scenario Outline: API: User should be able convert currency "<originalCurrency>" to another currency ""<conversionCurrencySymbol>""using /tools/price-conversion call
    Given user retrieves the id of "<originalCurrency>"
    And user converts the above currency to "<currencyName>" - "<conversionCurrencySymbol>" for amount "<amount>"
    Then price of "<conversionCurrencySymbol>" is fetched successfully
    Examples:
      | originalCurrency    | currencyName            |  conversionCurrencySymbol  | amount     |
      | BTC                 | Bolivian Boliviano      | BOB                        | 1          |
      | USDT                | Bolivian Boliviano      | BOB                        | 10         |
      | ETH                 | Bolivian Boliviano      | BOB                        | 100        |


  @BACKEND_TASK2
  Scenario Outline:API: User should be validate data fetched from cryptocurrency/info for "<currencySymbol>"
    Given user retrieves the technical documentation of "<currencyId>"
    And user validates all the required information
    | currencySymbol   | currencyId     | technicalDoc   | dateAdded      | tag    |
    | <currencySymbol> | <currencyId>   | <technicalDoc> | <dateAdded>    | <tag>  |

    Examples:
      | currencySymbol   | currencyId     | technicalDoc                                        | dateAdded                 | tag      |
      | ETH              | 1027           | https://github.com/ethereum/wiki/wiki/White-Paper    | 2015-08-07T00:00:00.000Z  | mineable |

  @BACKEND_TASK3
  Scenario: API: User should be validate cryptocurrencies and mineable tags from cryptocurrency/info for the first 10 ids
    Given user retrieves the technical documentation of first ten ids
    Then print the list of the currencies that have mineable tag
