package com.example.views;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Set;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class HomeView extends VerticalLayout {

    public HomeView() {

        DatePicker notFriday = new DatePicker("Don't pick a Friday");
        notFriday.setLocale(Locale.US);
        notFriday.setDisabledWeekdays(Set.of(DayOfWeek.FRIDAY));
        add(notFriday);

        DatePicker notWeekends = new DatePicker("Don't pick weekends");
        notWeekends.setDisabledWeekdays(Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY));
        add(notWeekends);

        DatePicker specificDisallowed = new DatePicker("Do not pick 25.7.2026 or 25.8.2026");
        Set<LocalDate> specificDates = Set.of(LocalDate.of(2026, 7, 25), LocalDate.of(2026, 8, 25));
        specificDisallowed.setDisabledDates(specificDates);
        add(specificDisallowed);

        DatePicker disallowDivisibleByThree = new DatePicker("Do not pick 3,6,9,12,... of any month");
        disallowDivisibleByThree.setDisabledDatesProvider(date -> date.getDayOfMonth() % 3 == 0);
        add(disallowDivisibleByThree);

        DatePicker someDaysAreBooked = new DatePicker("Days 4,8,12,... are half booked and 2,6,10,14,... fully booked");
        someDaysAreBooked.setDatePartNameGenerator(date -> {
            if (isHalfBooked(date)) {
                return "half";
            } else if (isFullyBooked(date)) {
                return "full";
            }
            return null;
        });
        someDaysAreBooked.setDisabledDatesProvider(this::isFullyBooked);

        add(someDaysAreBooked);

    }

    private boolean isHalfBooked(LocalDate date) {
        return (date.getDayOfMonth()) % 4 == 0;
    }

    private boolean isFullyBooked(LocalDate date) {
        return (date.getDayOfMonth() + 2) % 4 == 0;
    }
}
