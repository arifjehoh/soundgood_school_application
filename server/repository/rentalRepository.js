const connection = require('../repository/mysqlconfig');

//const mysql = require ('mysql');
/*const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '1234',
    database: 'school'
});

function getInstruments() {
    connection.connect();
    connection.query (
        'SELECT * FROM `instrument_rental` WHERE `rental_id` IS NOT NULL',
        function (err, rows, fields) {
            console.log (err);
            if (err) throw err
            console.log (rows);
        }
    );
    connection.end();
};
const pool = mysql.createPool ({
    connectionLimit: 10,
    host: 'localhost',
    user: 'root',
    password: '1234',
    database: 'school'
});
*/
exports.getInstruments = () => {
    console.log ('Get Instruments from MySQL');
    /*pool.getConnection ( function(err, connection) {
        if (err) {
            console.log(err);
            throw err;
        }

        console.log("I'm here!");
        conncetion.query ('SELECT * FROM instrument_rental WHERE rental_id IS NOT NULL', 
            function (error, results, fields) {
                connection.release();
                console.log (`Results: ${results}`);

                if (error) throw error;
            }
        );
    });
    */
    connection.query ('SELECT 1', (err, res, fields) => {
        if (err) throw err;
        console.log ('Get Instrumentssss');
    });
};
