from tkinter import filedialog

import cv2
import matplotlib.pyplot as plt
import tkinter as tk

from fracture_detect import CannyDetector, HarrisCornerDetection

def ImageProcessing(image_path):
    frame = cv2.imread(image_path)
    frame = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    frame = cv2.bilateralFilter(frame, 9, 75, 75)
    canny_img = CannyDetector(frame)

    cv2.imwrite("canny.jpg", canny_img)

    firstimage = cv2.imread("canny.jpg", cv2.IMREAD_GRAYSCALE)
    w, h = firstimage.shape

    bgr = cv2.cvtColor(firstimage, cv2.COLOR_GRAY2RGB)

    R = HarrisCornerDetection(firstimage)
    ConnerStrengthThreshold = 200000000

    radius = 2
    color = (255, 0, 0)
    thickness = 1
    center_coordinates = (10, 5)
    image = cv2.circle(firstimage, center_coordinates, radius, color, thickness)

    PointList = []

    for row in range(w):
        for col in range(h):
            if R[row][col] > ConnerStrengthThreshold:
                max = R[row][col]
                skip = False
                for nrow in range(5):
                    for ncol in range(5):
                        if row + nrow - 2 < w and col + ncol - 2 < h:
                            if R[row + nrow - 2][col + ncol - 2] > max:
                                skip = True
                                break
                if not skip:
                    cv2.circle(bgr, (col, row), radius, color, thickness)
                    PointList.append((row, col))

    outname = "harrising" + ".png"
    cv2.imwrite(outname, bgr)

    f, plots = plt.subplots(1, 3)
    plots[0].imshow(frame)
    plots[0].set_title('Filtered Image')
    plots[1].imshow(canny_img)
    plots[1].set_title('Canny Edge Detection')
    plots[2].imshow(bgr)
    plots[2].set_title('Harris Corner Detection')

    plt.show()

def browse_file():
    initial_dir = "C:\\Users\\PC\\Downloads\\XLA\\data_test"
    filename = filedialog.askopenfilename(
        initialdir=initial_dir,
        title="Select an Image",
        filetypes=(("jpeg files", "*.jpg"), ("png files", "*.png"), ("all files", "*.*"))
    )
    if filename:
        ImageProcessing(filename)

def app():
    root = tk.Tk()
    root.title("Bone Fracture Detection")
    root.geometry("800x600")

    header_label = tk.Label(root, text="Bone Fracture Detection", font=("Helvetica", 36))
    header_label.pack(pady=36)

    browse_button = tk.Button(root, text="Browse File", command=browse_file)
    browse_button.pack(pady=36)

    root.mainloop()


if __name__ == '__main__':
    # Commandline
    # image_path = "C:\\Users\\PC\\Downloads\\XLA\\data_test\\1.jpg"
    # ImageProcessing(image_path)

    # Application
    app()
