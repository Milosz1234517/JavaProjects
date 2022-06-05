var nextGeneration = function (matrix, ants) {

    for (var i = 0; i < ants.length; i++) {
        var y = ants[i][0];
        var x = ants[i][1];
        if (ants[i][2] === 2) {
            if (matrix[y][x] === 0) {
                matrix[y][x] = 1;
                x = x + 1;
                ants[i][2] = 3;
                if (x >= matrix[0].length) {
                    ants[i][1] = 0;
                } else {
                    ants[i][1] = x;
                }
            } else {
                matrix[y][x] = 0;
                x = x - 1;
                ants[i][2] = 5;
                if (x < 0) {
                    ants[i][1] = matrix[0].length - 1;
                } else {
                    ants[i][1] = x;
                }
            }
        } else if (ants[i][2] === 4) {
            if (matrix[y][x] === 0) {
                matrix[y][x] = 1;
                x = x - 1;
                ants[i][2] = 5;
                if (x < 0) {
                    ants[i][1] = matrix[0].length - 1;
                } else {
                    ants[i][1] = x;
                }
            } else {
                matrix[y][x] = 0;
                x = x + 1;
                ants[i][2] = 3;
                if (x >= matrix[0].length) {
                    ants[i][1] = 0;
                } else {
                    ants[i][1] = x;
                }
            }
        } else if (ants[i][2] === 3) {
            if (matrix[y][x] === 0) {
                matrix[y][x] = 1;
                y = y + 1;
                ants[i][2] = 4;
                if (y >= matrix.length) {
                    ants[i][0] = 0;
                } else {
                    ants[i][0] = y;
                }
            } else {
                matrix[y][x] = 0;
                y = y - 1;
                ants[i][2] = 2;
                if (y < 0) {
                    ants[i][0] = matrix.length - 1;
                } else {
                    ants[i][0] = y;
                }
            }
        } else if (ants[i][2] === 5) {
            if (matrix[y][x] === 0) {
                matrix[y][x] = 1;
                y = y - 1;
                ants[i][2] = 2;
                if (y < 0) {
                    ants[i][0] = matrix.length - 1;
                } else {
                    ants[i][0] = y;
                }
            } else {
                matrix[y][x] = 0;
                y = y + 1;
                ants[i][2] = 4;
                if (y >= matrix.length) {
                    ants[i][0] = 0;
                } else {
                    ants[i][0] = y;
                }
            }
        }
    }

    return {mat: matrix, ant: ants};
};