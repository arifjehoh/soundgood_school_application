const express = require('express');
const bodyparser = require('body-parser');
const cors = require('cors');
const db = require('./application/models');
const app = express();

app.use(bodyparser.json());
db.sequelize.sync();

// Default home route
app.get('/', (req,res) => {
    res.json({message: 'Welcome to Soundgood School'});
});

require('./application/routes/routes')(app);

const PORT = process.env.PORT || 8081;

app.listen(PORT, () => {
    console.log('Welcome to Soundgood School');
});

