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
router.use(express.json());

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

//crea PDF 
router.post('/createPDF', function(req, res, next) {
  const doc = new PDFDocument;
  doc.pipe(fs.createWriteStream('file.pdf'));
  console.log('In server...');
  var bean = req.body;
  doc.text(JSON.stringify(bean));
  console.log(JSON.stringify(bean));
  doc.end();
  res.download('192.168.1.4:3000/file.pdf', 'file.pdf'); //non funziona
  
});

router.get('/boh/:id',function(req,res,next){
  res.send('boh' + req.params.id);
});

router.post('/sub', function(req,res,next){
  var id = 5;
  res.redirect('/boh/' + ' ' + id);
});





module.exports = router;
