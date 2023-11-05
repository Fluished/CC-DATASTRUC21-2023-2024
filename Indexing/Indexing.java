import java.util.*;

class MyArray {
    private int[] array;
    private int arraySize, value, count;
    private Scanner scan = new Scanner(System.in);

    MyArray(int arraySize) {
        this.arraySize = arraySize;
        this.array = new int[arraySize];
        value = 0;
        count = 0;
    }

    boolean isFull() {
        return count >= arraySize;
    }

    void addElement(int value) {
        array[count] = value;
        count++;
    }

    void viewElements() {
        System.out.println("\n[ Array: " + Arrays.toString(array) + " ]");
    }

    void removeIndex(int arrayIndex) {
        if (arrayIndex >= 0 && arrayIndex < arraySize) {
            for (int i = 0; i < arraySize; i++) {
                if (i == arrayIndex) {
                    array[arrayIndex] = 0;
                }
            }
        } else {
            System.out.println("\n[ Index out of bounds! ]");
        }
    }

    void searchValue(int value) {
        boolean checkValue = false;

        for (int i = 0; i < count; i++) {
            if (array[i] == value) {
                System.out.println("\n[ " + value + " is in index " + i + " ]");
                checkValue = true;
            }
        }

        if (checkValue == false) {
            System.out.println("\n[ " + value + " is not in the array ]");
        }
    }

    void sortAscending() {
        System.out.println("\n[ Array Before Sort: ]");
        System.out.println("[ Array: " + Arrays.toString(array) + " ]");

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 1; j < (array.length - i); j++) {
                if (array[j - 1] > array[j]) {
                    value = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = value;
                }
            }
        }
        System.out.println("\n[ Array After Sort: ]");
        System.out.println("[ Array: " + Arrays.toString(array) + " ]");
    }

    void sortDescending() {
        System.out.println("\n[ Array Before Sort: ]");
        System.out.println("[ Array: " + Arrays.toString(array) + " ]");

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 1; j < (array.length - i); j++) {
                if (array[j - 1] < array[j]) {
                    value = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = value;
                }
            }
        }
        System.out.println("\n[ Array After Sort: ]");
        System.out.println("[ Array: " + Arrays.toString(array) + " ]");
    }

    void editIndex(int arrayIndex) {
        if (arrayIndex < arraySize) {
            System.out.println("\n[ Enter new element: ]");
            System.out.print(" == ");
            value = scan.nextInt();
            for (int i = 0; i < array.length; i++) {
                if (i == arrayIndex) {
                    array[arrayIndex] = value;
                }
            }
        } else {
            System.out.println("\n[ Index out of bounds! ]");
        }
    }
}

class Indexing {
    public static void main(String[] args) {
        int arraySize = 0, option, arrayIndex, value, sortOption;
        boolean isError = false;
        Scanner scan = new Scanner(System.in);

        do {
            try {
                System.out.println("\n[ Create Array Size: ]");
                System.out.print(" == ");
                arraySize = scan.nextInt();

                if (arraySize < 1) {
                    System.out.println("\n[ Array size must be greater than 0! ]");
                }
            } catch (Exception e) {
                System.out.println("\n[ Array size must be greater than 0! ]");
                scan.next();
            }
        } while (arraySize < 1);

        MyArray myArray = new MyArray(arraySize);

        do {
            try {
                System.out.println("\n[ 1. Add Element ]");
                System.out.println("[ 2. View Elements ]");
                System.out.println("[ 3. Remove Index Value ]");
                System.out.println("[ 4. Search Value in Array ]");
                System.out.println("[ 5. Sort Array ]");
                System.out.println("[ 6. Edit Index Value ]");
                System.out.println("[ 7. Exit ]");
                System.out.print(" == ");
                option = scan.nextInt();

                switch (option) {
                    case 1:
                        do {
                            try {
                                if (!myArray.isFull()) {
                                    System.out.println("\n[ Enter element to add: ]");
                                    System.out.print(" == ");
                                    value = scan.nextInt();
                                    myArray.addElement(value);
                                } else {
                                    System.out.println("\n[ Array is full! ]");
                                }
                            } catch (Exception e) {
                                System.out.println("\n[ Wrong Input! ]");
                                scan.next();
                            }
                        } while (!myArray.isFull());
                        break;
                    case 2:
                        myArray.viewElements();
                        break;
                    case 3:
                        do {
                            try {
                                System.out.println("\n[ Enter index to remove: ]");
                                System.out.print(" == ");
                                arrayIndex = scan.nextInt();
                                myArray.removeIndex(arrayIndex);
                                isError = false;
                            } catch (Exception e) {
                                System.out.println("\n[ Wrong Input! ]");
                                scan.next();
                                isError = true;
                            }
                        } while (isError == true);
                        break;
                    case 4:
                        do {
                            try {
                                System.out.println("\n[ Enter value to search: ]");
                                System.out.print(" == ");
                                value = scan.nextInt();
                                myArray.searchValue(value);
                                isError = false;
                            } catch (Exception e) {
                                System.out.println("\n[ Wrong Input! ]");
                                scan.next();
                                isError = true;
                            }
                        } while (isError == true);
                        break;
                    case 5:
                        do {
                            try {
                                System.out.println("\n[ Sort By: ]");
                                System.out.println("[ 1. Ascending ]");
                                System.out.println("[ 2. Descending ]");
                                System.out.print(" == ");
                                sortOption = scan.nextInt();

                                switch (sortOption) {
                                    case 1:
                                        myArray.sortAscending();
                                        break;
                                    case 2:
                                        myArray.sortDescending();
                                        break;
                                    default:
                                        System.out.println("\n[ Option not allowed ]");
                                        break;
                                }
                            } catch (Exception e) {
                                System.out.println("\n[ Wrong Input! ]");
                                scan.next();
                                sortOption = 3;
                            }
                        } while (sortOption < 1 || sortOption > 2);
                        break;
                    case 6:
                        do {
                            try {
                                System.out.println("\n[ Enter index to edit: ]");
                                System.out.print(" == ");
                                arrayIndex = scan.nextInt();
                                myArray.editIndex(arrayIndex);
                                isError = false;
                            } catch (Exception e) {
                                System.out.println("\n[ Wrong Input! ]");
                                scan.next();
                                isError = true;
                            }
                        } while (isError == true);
                        break;
                    case 7:
                        break;
                    default:
                        System.out.println("\n[ Option not allowed ]");
                        break;
                }
            } catch (Exception e) {
                System.out.println("\n[ Wrong Input! ]");
                scan.next();
                option = 8;
            }
        } while (option != 7);

        scan.close();
    }
}