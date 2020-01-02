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
const storage = admin.storage();
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
  storage.bucket("gs://porting-android-is.appspot.com").upload('D:/Documenti/GitHub/ANDROID-IS/app/src/main/node/file.pdf',
  function(err, file) {
    if (!err) {
      console.log('File caricato');
      res.send('200');
    }
    else{
      console.log(err);
    }
  });
});


router.get('/boh/:id',function(req,res,next){
  res.send('boh' + req.params.id);
});

router.post('/sub', function(req,res,next){
  var id = 5;
  res.redirect('/boh/' + ' ' + id);
});





module.exports = router;
