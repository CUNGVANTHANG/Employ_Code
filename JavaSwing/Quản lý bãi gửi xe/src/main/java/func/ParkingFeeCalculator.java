package func;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ParkingFeeCalculator {
    public static double calculateFee(String entryTime, String exitTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
            Date entryDate = sdf.parse(entryTime);
            Date exitDate = sdf.parse(exitTime);
            long differenceInMilliseconds = exitDate.getTime() - entryDate.getTime();
            long differenceInHours = differenceInMilliseconds / (1000 * 60 * 60);

            double fee = 3000; // Giá mặc định là 3 nghìn

            // Nếu số giờ vượt quá 4 giờ, tăng thêm 2000
            if (differenceInHours > 4) {
                fee += 2000;
            }

            return fee;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 3000; // Giá mặc định nếu có lỗi xảy ra
    }

}
