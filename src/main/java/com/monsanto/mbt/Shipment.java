package com.monsanto.mbt;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * This program is for the shipping specialist that allows the specialist two options for creating
 * shipments.
 *
 * <p>One of the features the program needs to have is to allow the specialist to sort the Widgets
 * by date or by color to assist the specialist with shipment creation.
 *
 * <p>One of the constraints on shipment creation is that a shipment can hold a maximum of 10
 * Widgets.
 *
 * @author VIVEK KUMAR SINGH
 * @since (2018-08-21 13:18:13)
 */
public class Shipment {

  /**
   * This method will help the specialist to create shipment by sorting the widgets on the basis of
   * date.
   *
   * @param widgets Takes the raw list of widgets as input.
   * @param dateComparator Date comparator object for sorting according to passed comparator. This
   *     parameter is optional, if user will not pass the comparator object then list will be sorted
   *     in assenting order.
   * @return According to constraint we can only hold 10 widgets in a shipment. hence result will
   *     contains only 10 elements.
   */
  public List<List<Widget>> sortWidgetsByDate(
      List<Widget> widgets, Comparator<Widget> dateComparator) {

    return createShipment(widgets, dateComparator, Widget::getProductionDate, null);
  }

  /**
   * This method will help the specialist to create shipment by sorting the widgets on the basis of
   * color.
   *
   * @param widgets Takes the raw list of widgets as input.
   * @param colorComparator Color comparator object for sorting according to passed comparator. This
   *     parameter is optional, if user will not pass the comparator object then list will be sorted
   *     in assenting order.
   * @return According to constraint we can only hold 10 widgets in a shipment. hence result will
   *     contains only 10 elements.
   */
  public List<List<Widget>> sortWidgetsByColor(
      List<Widget> widgets, Comparator<Widget> colorComparator) {

    return createShipment(widgets, colorComparator, null, Widget::getColor);
  }

  /**
   * This utility method is actually create the shipments on the basis of different requirements.
   *
   * @param widgets Takes the raw list of widgets as input.
   * @param comparator comparator object that will sort the widgets before shipping.
   * @param dateFunction data Function type object that will be passed in comparing function to
   *     compare date field
   * @param colorFunction color Function type object that will be passed in comparing function to
   *     compare color field
   * @return shipments list(container of shipments)
   */
  private List<List<Widget>> createShipment(
      List<Widget> widgets,
      Comparator<Widget> comparator,
      Function<Widget, Date> dateFunction,
      Function<Widget, String> colorFunction) {

    /**
     * If list is empty then we can't create shipment so we need to stop here and response with the
     * relevant message.
     */
    if (widgets == null || widgets.size() < 1)
      throw new IllegalStateException("Warehouse is Empty!");

    /**
     * If comparator will not be passed then comparing function will be passed for sorting on the
     * basis of the passed field(i.e color or date field)
     */
    if (comparator == null) {
      if (dateFunction != null) comparator = Comparator.comparing(dateFunction);
      else if (colorFunction != null) comparator = Comparator.comparing(colorFunction);
    }

    widgets.sort(comparator);
    List<List<Widget>> shipments = new ArrayList<>();
    int from = 0, to = 0;

    for (int i = 0; i < Math.ceil(widgets.size() / 10.0); i++) {
      to += 10;
      to = Math.min(widgets.size(), to);

      shipments.add(new ArrayList<>(widgets.subList(from, to)));

      from = to;
    }

    return shipments;
  }
}
