import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class StrassenMatrixMultiplicationTest {
    private StrassenMatrixMultiplication strassenMatrixMultiplication;

//    此注解意思是所有的用例开始前，做的内容
    @BeforeAll
    static void beforeAll() {
        System.out.println("**--- Executed once before all test methods in this class ---**");
    }

//    此注解意思是每个测试开始前做的内容
    @BeforeEach
    void setUp() {
        strassenMatrixMultiplication = new StrassenMatrixMultiplication();
    }

    @AfterEach
    void tearDown() {
        strassenMatrixMultiplication = null;
    }

    @DisplayName("Test sub method")
//    @Disabled("Do not test the sub method temporarily.")
//    这个注解说明使要执行的测试用例
    @org.junit.jupiter.api.Test
    void sub() {
        int[][] A={{1,2},{3,4}};
        int[][] B={{5,6},{7,8}};
        int[][] C={{-4,-4},{-4,-4}};
        int n = A.length;
        int[][] D = strassenMatrixMultiplication.sub(A,B);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
//                断言矩阵C和矩阵D相等，如果不相等会报
                assertEquals(C[i][j],D[i][j]);
            }
        }
    }

    @ParameterizedTest
    @DisplayName("Test add method")
    @CsvSource({"1,2,3,4,5,6,7,8,6,8,10,12",
            "2,4,3,5,4,6,3,2,6,10,6,7",
            "11,2,6,4,9,16,7,3,20,18,13,7"})
    void add(int A00,int A01, int A10, int A11, int B00,int B01, int B10, int B11,
    int C00,int C01, int C10, int C11) {
        int[][] A={{A00,A01},{A10,A11}};
        int[][] B={{B00,B01},{B10,B11}};
        int[][] C={{C00,C01},{C10,C11}};
        int n = A.length;
        int[][] D = strassenMatrixMultiplication.add(A,B);
        CompareMatrix(C,D);
    }

    @DisplayName("Test split method")
    @org.junit.jupiter.api.Test
    void split() {

        int[][] A={{1,2,3,4},{4,5,6,7},{7,8,9,10},{4,5,6,7}};
        int n = A.length;
        int[][] B=new int[n/2][n/2];
        int[][] C={{9,10},{6,7}};
        strassenMatrixMultiplication.split(A, B, n / 2 , n / 2);

//        以下代码为CompareMatrix方法的功能，可以直接调用CompareMatrix方法
//        int n = C.length;
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                assertEquals(B[i][j],C[i][j]);
//            }
//        }
        CompareMatrix(B , C);
    }


    @DisplayName("Test join method")
    @org.junit.jupiter.api.Test
    void join() {
//        1 2 3 4
//        3 4 5 6
//        5 6 7 8
//        7 8 9 10
        int[][] A={{1,2,3,4},{3,4,5,6},{5,6,7,8},{7,8,9,10}};
        int[][] B=new int[4][4];
        int[][] A11 = {{1,2},{3,4}};
        int[][] A12 = {{3,4},{5,6}};
        int[][] A21 = {{5,6},{7,8}};
        int[][] A22 = {{7,8},{9,10}};
        int n = A.length;
        strassenMatrixMultiplication.join(A11, B, 0, 0);
        strassenMatrixMultiplication.join(A12, B, 0, n / 2);
        strassenMatrixMultiplication.join(A21, B, n / 2, 0);
        strassenMatrixMultiplication.join(A22, B, n / 2, n / 2);
        CompareMatrix(A, B);
    }

    @DisplayName("Test multiply method")
    @org.junit.jupiter.api.Test
    void multiply() {
        int[][] A = {{2,3},{4,1}};
        int[][] B = {{5,7},{6,8}};
        int[][] C = {{28,38},{26,36}};
        int[][] D = new int[2][2];
        D = strassenMatrixMultiplication.multiply(A,B);
        CompareMatrix(C,D);
    }

    void CompareMatrix(int[][] C1, int[][]C2)
    {
        int n = C1.length;
        assumeTrue(n>0);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                assertEquals(C1[i][j],C2[i][j]);
            }
        }
    }
}

// 2024/4/17 16:57 实验3 完成3个todo join split 和 multiply的， 都是只写一组没有用参数化