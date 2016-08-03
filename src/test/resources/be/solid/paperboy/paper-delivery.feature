Feature: Deliver papers to customer
  ########################################

  Scenario Outline: Single customer delivery
    Given a single customer with <CustomerStartMoney> eur
    And a single paperboy
    And the price of a paper is <PaperPrice> eur
    When the paper is delivered
    Then the paperboy sold <NrOfPapersSold> newspapers for a total of <Revenue> eur
    And the single customer has <CustomerEndMoney> eur left and owns a paper state is <PaperState>
    And the amount in circulation remains unchanged
    Examples:
      | CustomerStartMoney | PaperPrice | NrOfPapersSold | Revenue | CustomerEndMoney | PaperState |
      | 0                  | 1          | 0              | 0       | 0                | false      |
      | 1                  | 1          | 1              | 1       | 0                | true       |
      | 2                  | 1          | 1              | 1       | 1                | true       |
      | 0                  | 2          | 0              | 0       | 0                | false      |
      | 1                  | 2          | 0              | 0       | 1                | false      |
      | 2                  | 2          | 1              | 2       | 0                | true       |


########################################

  Scenario: Single street delivery
    Given The initial customer money distribution:
      | Street     | HouseNr | Money |
      | MainStreet | A       | 4     |
      | MainStreet | B       | 2     |
      | MainStreet | C       | 1     |
      | MainStreet | D       | 0     |
    And there are 1 paperboys
    And the price of a paper is 2 eur
    When the papers are delivered
    Then the paperboys sold 2 newspapers for a total of 4 eur
    And the amount in circulation remains unchanged
    And The resulting customer state:
      | Street     | HouseNr | Money | OwnsPaper |
      | MainStreet | A       | 2     | true      |
      | MainStreet | B       | 0     | true      |
      | MainStreet | C       | 1     | false     |
      | MainStreet | D       | 0     | false     |


########################################

  Scenario: City wide delivery
    Given The initial customer money distribution:
      | Street       | HouseNr | Money |
      | MainStreet   | A       | 10    |
      | MainStreet   | C       | 0     |
      | MainStreet   | D       | 2     |
      | MainStreet   | F       | 7     |
      | MainStreet   | G       | 10    |
      | ElmStreet    | B       | 10    |
      | ElmStreet    | D       | 10    |
      | ElmStreet    | E       | 5     |
      | ElmStreet    | G       | 20    |
      | SeasamStreet | A       | 10    |
      | SeasamStreet | C       | 10    |
      | SeasamStreet | E       | 0     |
      | SeasamStreet | F       | 8     |
      | Boulevard    | A       | 10    |
      | Boulevard    | C       | 10    |
      | Boulevard    | E       | 0     |
      | Boulevard    | F       | 1     |
    And there are 3 paperboys
    And the price of a paper is 2 eur
    When the papers are delivered
    Then the paperboys sold 13 newspapers for a total of 26 eur
    And the amount in circulation remains unchanged
    And The resulting customer state:
      | Street       | HouseNr | Money | OwnsPaper |
      | MainStreet   | A       | 8     | true      |
      | MainStreet   | C       | 0     | false     |
      | MainStreet   | D       | 0     | true      |
      | MainStreet   | F       | 5     | true      |
      | MainStreet   | G       | 8     | true      |
      | ElmStreet    | B       | 8     | true      |
      | ElmStreet    | D       | 8     | true      |
      | ElmStreet    | E       | 3     | true      |
      | ElmStreet    | G       | 18    | true      |
      | SeasamStreet | A       | 8     | true      |
      | SeasamStreet | C       | 8     | true      |
      | SeasamStreet | E       | 0     | false     |
      | SeasamStreet | F       | 6     | true      |
      | Boulevard    | A       | 8     | true      |
      | Boulevard    | C       | 8     | true      |
      | Boulevard    | E       | 0     | false      |
      | Boulevard    | F       | 1     | false     |