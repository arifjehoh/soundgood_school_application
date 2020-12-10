const mysql = require ('mysql');
/*const pool = mysql.createPool({
    connectionLimit: 10,
    port: 8081,
    host:'localhost',
    user: 'root',
    password: '1234',
    database: 'school'
});

module.exports = pool;
*/
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '1234',
    database: 'school'
});

module.exports = connection;
