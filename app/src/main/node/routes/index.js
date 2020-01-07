var express = require('express');
const fs = require ('fs');
var router = express.Router();
const admin = require ('firebase-admin');
const PDFDocument = require('pdfkit');
const serviceAccount = ('./serviceAccountKey.json');


admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});


const storage = admin.storage();
router.use(express.json());


/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

//crea PDF 
router.post('/createPDF', function(req, res, next) {
  const doc = new PDFDocument;
  var bean = req.body;
  var filename = req.body.user_key + '.pdf';
  doc.pipe(fs.createWriteStream(filename));
  console.log('In server...');
  doc.text(JSON.stringify(bean));
  console.log(JSON.stringify(bean));
  doc.end();
  storage.bucket("gs://porting-android-is.appspot.com").upload('C:/Users/Windows/Documents/GitHub/ANDROID-IS/app/src/main/node/'+filename,
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
