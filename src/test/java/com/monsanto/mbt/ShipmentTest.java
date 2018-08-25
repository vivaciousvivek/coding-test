package com.monsanto.mbt;

import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author VIVEK KUMAR SINGH
 * @since (2018-08-21 13:56:38)
 */
public class ShipmentTest {

  private static Shipment shipment;
  private static List<Widget> widgets;
  private static List<String> sortedAscColors;
  private static List<String> sortedDescColors;
  private static List<Date> sortedAscDates;
  private static List<Date> sortedDescDates;

  @BeforeClass
  public static void init() {
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
    List<List<Widget>> sortedShipmentsInAscending =
        assertShipmentsSize(shipment.sortWidgetsByColor(new ArrayList<>(widgets), null));

    // test the list in ascending order by color
    assertShipmentElementsByColorAndDate(
        sortedShipmentsInAscending,
        sortedAscColors.stream().collect(Collectors.toList()),
        Widget::getColor,
        (a, b) -> a.toString().compareTo(b.toString()) <= 0);
  }

  /** Sorted shipment by its color in descending order */
  @Test
  public void testShipment_Sorted_By_Color_In_Desc() {
    List<List<Widget>> sortedShipmentsInDescending =
        assertShipmentsSize(
            shipment.sortWidgetsByColor(
                new ArrayList<>(widgets), (w1, w2) -> w2.getColor().compareTo(w1.getColor())));

    // test the list in descending order by color
    assertShipmentElementsByColorAndDate(
        sortedShipmentsInDescending,
        sortedDescColors.stream().collect(Collectors.toList()),
        Widget::getColor,
        (a, b) -> a.toString().compareTo(b.toString()) >= 0);
  }

  /** Sorted shipment by its date in ascending order */
  @Test
  public void testShipment_Sorted_By_Date() {
    List<List<Widget>> sortedShipmentsInAscending =
        assertShipmentsSize(shipment.sortWidgetsByDate(new ArrayList<>(widgets), null));

    // test the list in ascending order by date
    assertShipmentElementsByColorAndDate(
        sortedShipmentsInAscending,
        sortedAscDates.stream().collect(Collectors.toList()),
        Widget::getProductionDate,
        (a, b) -> stringToDate(a.toString()).compareTo(stringToDate(b.toString())) <= 0);
  }

  /** Sorted shipment by its date in descending order */
  @Test
  public void testShipment_Sorted_By_Date_In_Desc() {
    List<List<Widget>> sortedShipmentsInDescending =
        assertShipmentsSize(
            shipment.sortWidgetsByDate(
                new ArrayList<>(widgets),
                (w1, w2) -> w2.getProductionDate().compareTo(w1.getProductionDate())));

    // test the list in descending order by date
    assertShipmentElementsByColorAndDate(
        sortedShipmentsInDescending,
        sortedDescDates.stream().collect(Collectors.toList()),
        Widget::getProductionDate,
        (a, b) -> stringToDate(a.toString()).compareTo(stringToDate(b.toString())) >= 0);
  }

  /**
   * This method will validate the correctness of size of every shipment
   *
   * @param sortedShipment sorted shipment list
   * @return
   */
  private List<List<Widget>> assertShipmentsSize(List<List<Widget>> sortedShipment) {
    // test no. of shipments according to widgets in warehouse.
    assertEquals(
        String.format(
            "No. of Shipments should be %d for %d Widgets of Warehouse",
            (int) Math.ceil(widgets.size() / 10.0), widgets.size()),
        (int) Math.ceil(widgets.size() / 10.0),
        sortedShipment.size());

    // test whether the every shipment is shipments list should have max 10 elements.
    for (List<Widget> shipment : sortedShipment) {
      assertTrue(
          "Every Shipment should not have more than 10 elements",
          shipment.size() > 0 && shipment.size() <= 10);
    }

    return sortedShipment;
  }

  /**
   * This method will validate the correctness of elements equality and their order of every
   * shipment.
   *
   * @param sortedShipments sorted shipment list
   * @param sortedList sorted list of both color and date
   * @param function function to take getter of color and date as input
   * @param compareFunction function to take input for comparison of color and date with operator.
   */
  private void assertShipmentElementsByColorAndDate(
      List<List<Widget>> sortedShipments,
      List<Object> sortedList,
      Function<Widget, Object> function,
      BiFunction<Object, Object, Boolean> compareFunction) {
    int idx = 0;
    for (int i = 0; i < sortedShipments.size(); i++, idx++) {
      for (int j = 0; j < sortedShipments.get(i).size() - 1; j++, idx++) {
        assertEquals(sortedList.get(idx), function.apply(sortedShipments.get(i).get(j)));

        assertTrue(
            compareFunction.apply(
                function.apply(sortedShipments.get(i).get(j)),
                function.apply(sortedShipments.get(i).get(j + 1))));
      }
    }
  }

  /**
   * @param strDate pass string date into pattern('EEE MMM dd HH:mm:ss z yyyy') by default toString
   *     method of Date will give this pattern string
   * @return Date object of passed date in string
   */
  private Date stringToDate(String strDate) {
    try {
      return new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(strDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return null;
  }
}
