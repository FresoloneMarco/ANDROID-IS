var express = require('express');
const fs = require ('fs');
var router = express.Router();
const admin = require ('firebase-admin');
const PDFDocument = require('pdfkit');
const serviceAccount = ('./serviceAccountKey.json');
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});
const db = admin.firestore();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

//crea PDF 
router.post('/createPDF', function(req, res, next) {
  const doc = new PDFDocument;
  var bean = req.body.requestBean;
  console.log(bean);
  
});

router.get('/boh/:id',function(req,res,next){
  res.send('boh' + req.params.id);
});

router.post('/sub', function(req,res,next){
  var id = 5;
  res.redirect('/boh/' + ' ' + id);
});





module.exports = router;
