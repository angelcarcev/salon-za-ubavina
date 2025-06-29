package mk.ukim.finki.web_seminarska.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class TimeSlot {
    private LocalDateTime startTime;
    private boolean isAvailable;

    public TimeSlot(LocalDateTime startTime, boolean isAvailable) {
        this.startTime = startTime;
        this.isAvailable = isAvailable;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
