'use strict';

var express = require('express');
var path = require('path');
var PORT = 3333;

var app = express();

app.use(express.static(path.join(__dirname, '../../')));
app.use(express.static('/storage'));

app.listen(PORT);
console.log('Running Wiki on http://localhost:' + PORT);