package com.monsanto.mbt;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author VIVEK KUMAR SINGH
 * @since (2018-08-21 13:56:38)
 */
public class ShipmentTest {

  private Shipment shipment;
  private List<Widget> widgets;
  private List<String> sortedAscColors;
  private List<String> sortedDescColors;
  private List<Date> sortedAscDates;
  private List<Date> sortedDescDates;

  @Before
  public void init() {
    shipment = new Shipment();
    widgets = WidgetUtils.getSampleWidgets();
    sortedAscColors = widgets.stream().map(Widget::getColor).collect(Collectors.toList());

    sortedDescColors = new ArrayList<>(sortedAscColors);

    Collections.sort(sortedAscColors);
    sortedDescColors.sort(Comparator.reverseOrder());

    sortedAscDates = widgets.stream().map(Widget::getProductionDate).collect(Collectors.toList());

    sortedDescDates = new ArrayList<>(sortedAscDates);

    Collections.sort(sortedAscDates);
    sortedDescDates.sort(Comparator.reverseOrder());
  }

  /** Sorted shipment by its color in ascending order */
  @Test
  public void testShipment_Sorted_By_Color() {
    // Ascending order sorting test
    List<Widget> sortedWidgetsInAscending =
        shipment.sortWidgetsByColor(new ArrayList<>(widgets), null);

    // test whether the sorted list not holding more than 10 elements.
    assertTrue(sortedWidgetsInAscending.size() > 0 && sortedWidgetsInAscending.size() <= 10);

    // test the list in ascending order by color
    for (int i = 0; i < sortedWidgetsInAscending.size() - 1; i++) {
      assertEquals(sortedAscColors.get(i), sortedWidgetsInAscending.get(i).getColor());

      assertTrue(
          sortedWidgetsInAscending
                  .get(i)
                  .getColor()
                  .compareTo(sortedWidgetsInAscending.get(i + 1).getColor())
              <= 0);
    }
  }

  /** Sorted shipment by its color in descending order */
  @Test
  public void testShipment_Sorted_By_Color_In_Desc() {
    // Descending order sorting test
    List<Widget> sortedWidgetsInDescending =
        shipment.sortWidgetsByColor(
            new ArrayList<>(widgets), (w1, w2) -> w2.getColor().compareTo(w1.getColor()));

    // test whether the sorted list not holding more than 10 elements.
    assertTrue(sortedWidgetsInDescending.size() > 0 && sortedWidgetsInDescending.size() <= 10);

    // test the list in descending order by color
    for (int i = 0; i < sortedWidgetsInDescending.size() - 1; i++) {
      assertEquals(sortedDescColors.get(i), sortedWidgetsInDescending.get(i).getColor());
      assertTrue(
          sortedWidgetsInDescending
                  .get(i)
                  .getColor()
                  .compareTo(sortedWidgetsInDescending.get(i + 1).getColor())
              >= 0);
    }
  }

  /** Sorted shipment by its date in ascending order */
  @Test
  public void testShipment_Sorted_By_Date() {
    // Ascending order sorting test
    List<Widget> sortedWidgetsInAscending =
        shipment.sortWidgetsByDate(new ArrayList<>(widgets), null);

    // test whether the sorted list not holding more than 10 elements.
    assertTrue(sortedWidgetsInAscending.size() > 0 && sortedWidgetsInAscending.size() <= 10);

    // test the list in ascending order by date
    for (int i = 0; i < sortedWidgetsInAscending.size() - 1; i++) {
      assertEquals(sortedAscDates.get(i), sortedWidgetsInAscending.get(i).getProductionDate());

      assertTrue(
          sortedWidgetsInAscending
                  .get(i)
                  .getProductionDate()
                  .compareTo(sortedWidgetsInAscending.get(i + 1).getProductionDate())
              <= 0);
    }
  }

  /** Sorted shipment by its date in descending order */
  @Test
  public void testShipment_Sorted_By_Date_In_Desc() {
    // Descending order sorting test
    List<Widget> sortedWidgetsInDescending =
        shipment.sortWidgetsByDate(
            new ArrayList<>(widgets),
            (w1, w2) -> w2.getProductionDate().compareTo(w1.getProductionDate()));

    // test whether the sorted list not holding more than 10 elements.
    assertTrue(sortedWidgetsInDescending.size() > 0 && sortedWidgetsInDescending.size() <= 10);

    // test the list in descending order by date
    for (int i = 0; i < sortedWidgetsInDescending.size() - 1; i++) {
      assertEquals(sortedDescDates.get(i), sortedWidgetsInDescending.get(i).getProductionDate());

      assertTrue(
          sortedWidgetsInDescending
                  .get(i)
                  .getProductionDate()
                  .compareTo(sortedWidgetsInDescending.get(i + 1).getProductionDate())
              >= 0);
    }
  }
}
