<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
    <title>Welcome eh</title>
</head>
<body class="bg-danger">
    
    <div class="container">
        <div class="container">
            <div class="row">
                <h2 class="text-center">
                    HAHA Io
                </h2>
            </div>
            <div class="row">
                <form action="emp-add" method="POST" class="for">
                    <div class="row mb-3">
                        <div class="col-lg-4">
                            <label for="" class="form-label"> Nom de l'employe </label>
                        </div>
                        <div class="col-lg-5">
                            <input type="text" name="name" class="form-control">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-lg-4">
                            <label for="" class="form-label"> ID de l'employe </label>
                        </div>
                        <div class="col-lg-5">
                            <input type="text" name="id" class="form-control">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-lg-4">
                            <label for="" class="form-label"> Date d'admission </label>
                        </div>
                        <div class="col-lg-5">
                            <input type="date" name="date" class="form-control">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <input type="submit" value="Enregistrer">
                    </div>
                </form>
            </div>
        </div>
    </div>

</body>
</html>