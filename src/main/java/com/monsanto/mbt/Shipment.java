package com.monsanto.mbt;

import java.util.Comparator;
import java.util.List;

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
   * This method will help the specialist to sort the widgets by date.
   *
   * @param widgets Takes the raw list of widgets as input.
   * @param dateComparator Date comparator object for sorting according to passed comparator. This
   *     parameter is optional, if user will not pass the comparator object them we will sort the
   *     list in assenting order.
   * @return According to constraint we can only hold 10 shipment. hence result will contains only
   *     10 elements.
   */
  public List<Widget> sortWidgetsByDate(List<Widget> widgets, Comparator<Widget> dateComparator) {
    if (widgets == null) throw new IllegalStateException("Input widgets can't be null");

    Comparator<Widget> comparator = dateComparator;
    if (dateComparator == null) {
      //        This Comparator can also be written using lambda expression like so =>
      //        comparator = (w1, w2) -> w1.getProductionDate().compareTo(w2.getProductionDate());

      comparator = Comparator.comparing(Widget::getProductionDate);
    }

    widgets.sort(comparator);

    return widgets.subList(0, 10);
  }

  /**
   * This method will help the specialist to sort the widgets by color.
   *
   * @param widgets Takes the raw list of widgets as input.
   * @param colorComparator Color comparator object for sorting according to passed comparator. This
   *     parameter is optional, if user will not pass the comparator object them we will sort the
   *     list in assenting order.
   * @return According to constraint we can only hold 10 shipment. hence result will contains only
   *     10 elements.
   */
  public List<Widget> sortWidgetsByColor(List<Widget> widgets, Comparator<Widget> colorComparator) {
    if (widgets == null) throw new IllegalStateException("Input widgets can't be null");

    Comparator<Widget> comparator = colorComparator;
    if (colorComparator == null) {
      //        This Comparator can also be written using lambda expression like so =>
      //        comparator = (w1, w2) -> w1.getColor().compareTo(w2.getColor());
      comparator = Comparator.comparing(Widget::getColor);
    }

    widgets.sort(comparator);

    return widgets.subList(0, 10);
  }
}
