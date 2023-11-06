package com.pfortbe22bgrupo2.parcialtp3.fillerdata

import android.content.Context
import com.pfortbe22bgrupo2.parcialtp3.entities.UserEntity
import com.pfortbe22bgrupo2.parcialtp3.models.Dog
import com.pfortbe22bgrupo2.parcialtp3.models.ImagesResponse
import com.pfortbe22bgrupo2.parcialtp3.utilities.ApiHandler
import com.pfortbe22bgrupo2.parcialtp3.utilities.DatabaseHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DogsList {
    fun fillDatabase(context: Context) {
        val databaseHandler = DatabaseHandler(context)
        val apiHandler = ApiHandler()

        CoroutineScope(Dispatchers.IO).launch {
            if(databaseHandler.insertUser(UserEntity(
                "jm_sarmiento",
                "Juan Martin Sarmiento",
                "1123478540",
                "https://api.multiavatar.com/jm_sarmiento.png"
            ))) {
                apiHandler.getBreedImages("boxer", 3, fun(images: ImagesResponse) {
                    if (images.status == ApiHandler.SUCCESS_MSG && images.message != null) {
                        var imageList = mutableListOf<String>()
                        for (image in images.message) {
                            imageList.add(image)
                        }

                        CoroutineScope(Dispatchers.IO).launch {
                            val id = databaseHandler.insertAdoption(
                                Dog(
                                    name = "Rex",
                                    age = 3,
                                    location = Dog.Location.BUENOS_AIRES.displayName,
                                    sex = Dog.MALE,
                                    weight = 15.5f,
                                    owner_username = "jm_sarmiento",
                                    owner = "Juan Martin Sarmiento",
                                    phone = "1123478540",
                                    text = "Rex is a friendly and energetic dog looking for a loving home. He enjoys long walks in the park and playing fetch. He's great with kids and other pets. If you're looking for a loyal companion, Rex might be the perfect addition to your family.",
                                    image_urls = imageList.toTypedArray(),
                                    isFavorite = false
                                    //arrayOf("https://static.fundacion-affinity.org/cdn/farfuture/PVbbIC-0M9y4fPbbCsdvAD8bcjjtbFc0NSP3lRwlWcE/mtime:1643275542/sites/default/files/los-10-sonidos-principales-del-perro.jpg", "https://www.eltiempo.com/files/image_640_428/uploads/2023/01/05/63b73981e5a99.jpeg", "https://www.65ymas.com/uploads/s1/10/30/25/5/bigstock-421234682_1_621x621.jpeg")
                                )
                            )
                        }
                    }
                })
            }

            if (databaseHandler.insertUser(UserEntity(
                "luci_11",
                "Lucía Palacios",
                "1145236789",
                "https://api.multiavatar.com/luci_11.png"
            ))) {
                apiHandler.getBreedImages("husky", 3, fun(images: ImagesResponse) {
                    if (images.status == ApiHandler.SUCCESS_MSG && images.message != null) {
                        var imageList = mutableListOf<String>()
                        for (image in images.message) {
                            imageList.add(image)
                        }

                        CoroutineScope(Dispatchers.IO).launch {
                            val id = databaseHandler.insertAdoption(
                                Dog(
                                    name = "Clara",
                                    age = 2,
                                    location = Dog.Location.BUENOS_AIRES.displayName,
                                    sex = Dog.FEMALE,
                                    weight = 5.5f,
                                    owner_username = "luci_11",
                                    owner = "Lucía Palacios",
                                    phone = "1145236789",
                                    text = "Clara is a friendly and energetic dog looking for a loving home. She enjoys long walks in the park and playing fetch. She's great with kids and other pets. If you're looking for a loyal companion, Clara might be the perfect addition to your family.",
                                    image_urls = imageList.toTypedArray(),
                                    isFavorite = false
                                    //arrayOf("https://cdn.wamiz.fr/cdn-cgi/image/format=auto,quality=80,width=1200,height=675,fit=cover/article/main-picture/6391e5e6004f2924156742.jpg", "https://www.bunko.pet/__export/1638228692080/sites/debate/img/2021/11/29/cocker_spaniel_crop1638228652251.jpeg_554688468.jpeg")
                                )
                            )
                        }
                    }
                })
            }

            if (databaseHandler.insertUser(UserEntity(
                "messi_10",
                "Lionel Messi",
                "1145645345",
                "https://api.multiavatar.com/messi_10.png"
            ))) {
                apiHandler.getSubBreedImages("waterdog", "spanish", 3, fun(images: ImagesResponse) {
                    if (images.status == ApiHandler.SUCCESS_MSG && images.message != null) {
                        var imageList = mutableListOf<String>()
                        for (image in images.message) {
                            imageList.add(image)
                        }

                        CoroutineScope(Dispatchers.IO).launch {
                            val id = databaseHandler.insertAdoption(
                                Dog(
                                    name = "Hulk",
                                    age = 3,
                                    location = Dog.Location.BUENOS_AIRES.displayName,
                                    sex = Dog.MALE,
                                    weight = 7f,
                                    owner_username = "messi_10",
                                    owner = "Lionel Messi",
                                    phone = "1145645345",
                                    text = "Hulk is a friendly and energetic dog looking for a loving home. He enjoys long walks in the park and playing fetch. He's great with kids and other pets. If you're looking for a loyal companion, Hulk might be the perfect addition to your family.",
                                    image_urls = imageList.toTypedArray(),
                                    isFavorite = false
                                    //arrayOf("https://www.terranea.es/assets/images/razas/dogo_de_burdeos.jpg", "https://estaticos-cdn.elperiodico.com/clip/54639c02-8fda-41dd-bc03-1e7c81a3e2fb_16-9-aspect-ratio_default_0.jpg")
                                )
                            )
                        }
                    }
                })
            }

            if (databaseHandler.insertUser(UserEntity(
                "agente_j",
                "Darrell James Edwards",
                "1154564234",
                "https://api.multiavatar.com/agente_j.png"
            ))) {
                apiHandler.getSubBreedImages("sheepdog", "shetland", 3, fun(images: ImagesResponse) {
                    if (images.status == ApiHandler.SUCCESS_MSG && images.message != null) {
                        var imageList = mutableListOf<String>()
                        for (image in images.message) {
                            imageList.add(image)
                        }

                        CoroutineScope(Dispatchers.IO).launch {
                            val id = databaseHandler.insertAdoption(
                                Dog(
                                    name = "Frank",
                                    age = 2,
                                    location = Dog.Location.CABA.displayName,
                                    sex = Dog.MALE,
                                    weight = 5.5f,
                                    owner_username = "agente_j",
                                    owner = "Darrell James Edwards",
                                    phone = "1154564234",
                                    text = "Frank is a friendly and energetic dog looking for a loving home. He enjoys long walks in the park and playing fetch. He's great with kids and other pets. If you're looking for a loyal companion, Frank might be the perfect addition to your family.",
                                    image_urls = imageList.toTypedArray(),
                                    isFavorite = false
                                    //arrayOf("https://ichef.bbci.co.uk/news/660/cpsprodpb/48DD/production/_107435681_perro1.jpg", "https://images.hola.com/imagenes/mascotas/20200831174373/razas-de-perro-mas-populares-pug-carlino/0-860-24/pug-m.jpg?tx=w_680", "https://tecolotito.elsiglodedurango.com.mx/cdn-cgi/image/format=auto,width=1024/i/2023/04/1163645.jpeg")
                                )
                            )
                        }
                    }
                })
            }

            if (databaseHandler.insertUser(UserEntity(
                "marito_baraku",
                "Mario Gutierrez",
                "1148523679",
                "https://api.multiavatar.com/marito_baraku.png"
            ))) {
                apiHandler.getSubBreedImages("mastiff", "english", 3, fun(images: ImagesResponse) {
                    if (images.status == ApiHandler.SUCCESS_MSG && images.message != null) {
                        var imageList = mutableListOf<String>()
                        for (image in images.message) {
                            imageList.add(image)
                        }

                        CoroutineScope(Dispatchers.IO).launch {
                            val id = databaseHandler.insertAdoption(
                                Dog(
                                    name = "Lucho",
                                    age = 4,
                                    location = Dog.Location.BUENOS_AIRES.displayName,
                                    sex = Dog.MALE,
                                    weight = 3.5f,
                                    owner_username = "marito_baraku",
                                    owner = "Mario Gutierrez",
                                    phone = "1148523679",
                                    text = "Lucho is a friendly and energetic dog looking for a loving home. He enjoys long walks in the park and playing fetch. He's great with kids and other pets. If you're looking for a loyal companion, Lucho might be the perfect addition to your family.",
                                    image_urls = imageList.toTypedArray(),
                                    isFavorite = false
                                    //arrayOf("https://www.semana.com/resizer/wb9TzO0TSIMFaezcfeSimooW3PE=/1920x0/smart/filters:format(jpg):quality(80)/cloudfront-us-east-1.images.arcpublishing.com/semana/O5OGWSXAKFDPJDV3ZVJV2ZQXEY.jpg", "https://static.abc.es/media/ciencia/2022/04/28/AdobeStock_293422760-kvsC--1200x630@abc.jpg")
                                )
                            )
                        }
                    }
                })
            }

            if(databaseHandler.insertUser(UserEntity(
                    "zotikos_paco",
                    "Zotikos Paco",
                    "8502181308",
                    "https://api.multiavatar.com/zotikos_paco.png"
                ))) {
                apiHandler.getBreedImages("schipperke", 3, fun(images: ImagesResponse) {
                    if (images.status == ApiHandler.SUCCESS_MSG && images.message != null) {
                        var imageList = mutableListOf<String>()
                        for (image in images.message) {
                            imageList.add(image)
                        }

                        CoroutineScope(Dispatchers.IO).launch {
                            val id = databaseHandler.insertAdoption(
                                Dog(
                                    name = "Kostas",
                                    age = 4,
                                    location = Dog.Location.MENDOZA.displayName,
                                    sex = Dog.FEMALE,
                                    weight = 25.4f,
                                    owner_username = "zotikos_paco",
                                    owner = "Zotikos Paco",
                                    phone = "8502181308",
                                    text = "Kostas is a friendly and energetic dog looking for a loving home. He enjoys long walks in the park and playing fetch. He's great with kids and other pets. If you're looking for a loyal companion, Rex might be the perfect addition to your family.",
                                    image_urls = imageList.toTypedArray(),
                                    isFavorite = false
                                    //arrayOf("https://static.fundacion-affinity.org/cdn/farfuture/PVbbIC-0M9y4fPbbCsdvAD8bcjjtbFc0NSP3lRwlWcE/mtime:1643275542/sites/default/files/los-10-sonidos-principales-del-perro.jpg", "https://www.eltiempo.com/files/image_640_428/uploads/2023/01/05/63b73981e5a99.jpeg", "https://www.65ymas.com/uploads/s1/10/30/25/5/bigstock-421234682_1_621x621.jpeg")
                                )
                            )
                        }
                    }
                })
            }

            if(databaseHandler.insertUser(UserEntity(
                    "glauco_walpurga",
                    "Glauco Walpurga",
                    "3779892243",
                    "https://api.multiavatar.com/glauco_walpurga.png"
                ))) {
                apiHandler.getBreedImages("tervuren", 3, fun(images: ImagesResponse) {
                    if (images.status == ApiHandler.SUCCESS_MSG && images.message != null) {
                        var imageList = mutableListOf<String>()
                        for (image in images.message) {
                            imageList.add(image)
                        }

                        CoroutineScope(Dispatchers.IO).launch {
                            val id = databaseHandler.insertAdoption(
                                Dog(
                                    name = "Hardy",
                                    age = 5,
                                    location = Dog.Location.MISIONES.displayName,
                                    sex = Dog.MALE,
                                    weight = 65.4f,
                                    owner_username = "glauco_walpurga",
                                    owner = "Glauco Walpurga",
                                    phone = "3779892243",
                                    text = "Hardy is a friendly and energetic dog looking for a loving home. He enjoys long walks in the park and playing fetch. He's great with kids and other pets. If you're looking for a loyal companion, Rex might be the perfect addition to your family.",
                                    image_urls = imageList.toTypedArray(),
                                    isFavorite = false
                                    //arrayOf("https://static.fundacion-affinity.org/cdn/farfuture/PVbbIC-0M9y4fPbbCsdvAD8bcjjtbFc0NSP3lRwlWcE/mtime:1643275542/sites/default/files/los-10-sonidos-principales-del-perro.jpg", "https://www.eltiempo.com/files/image_640_428/uploads/2023/01/05/63b73981e5a99.jpeg", "https://www.65ymas.com/uploads/s1/10/30/25/5/bigstock-421234682_1_621x621.jpeg")
                                )
                            )
                        }
                    }
                })
            }

            if(databaseHandler.insertUser(UserEntity(
                    "hiezecihel_grozdana",
                    "Hiezecihel Grozdana",
                    "2652523108",
                    "https://api.multiavatar.com/hiezecihel_grozdana.png"
                ))) {
                apiHandler.getBreedImages("newfoundland", 3, fun(images: ImagesResponse) {
                    if (images.status == ApiHandler.SUCCESS_MSG && images.message != null) {
                        var imageList = mutableListOf<String>()
                        for (image in images.message) {
                            imageList.add(image)
                        }

                        CoroutineScope(Dispatchers.IO).launch {
                            val id = databaseHandler.insertAdoption(
                                Dog(
                                    name = "Edvard",
                                    age = 7,
                                    location = Dog.Location.CHUBUT.displayName,
                                    sex = Dog.MALE,
                                    weight = 46.2f,
                                    owner_username = "hiezecihel_grozdana",
                                    owner = "Hiezecihel Grozdana",
                                    phone = "2652523108",
                                    text = "Edvard is a friendly and energetic dog looking for a loving home. He enjoys long walks in the park and playing fetch. He's great with kids and other pets. If you're looking for a loyal companion, Rex might be the perfect addition to your family.",
                                    image_urls = imageList.toTypedArray(),
                                    isFavorite = false
                                    //arrayOf("https://static.fundacion-affinity.org/cdn/farfuture/PVbbIC-0M9y4fPbbCsdvAD8bcjjtbFc0NSP3lRwlWcE/mtime:1643275542/sites/default/files/los-10-sonidos-principales-del-perro.jpg", "https://www.eltiempo.com/files/image_640_428/uploads/2023/01/05/63b73981e5a99.jpeg", "https://www.65ymas.com/uploads/s1/10/30/25/5/bigstock-421234682_1_621x621.jpeg")
                                )
                            )
                        }
                    }
                })
            }

            if(databaseHandler.insertUser(UserEntity(
                    "qillaq_aapo",
                    "Qillaq Aapo",
                    "1594673850",
                    "https://api.multiavatar.com/qillaq_aapo.png"
                ))) {
                apiHandler.getBreedImages("shiba", 3, fun(images: ImagesResponse) {
                    if (images.status == ApiHandler.SUCCESS_MSG && images.message != null) {
                        var imageList = mutableListOf<String>()
                        for (image in images.message) {
                            imageList.add(image)
                        }

                        CoroutineScope(Dispatchers.IO).launch {
                            val id = databaseHandler.insertAdoption(
                                Dog(
                                    name = "Madelief",
                                    age = 7,
                                    location = Dog.Location.LA_RIOJA.displayName,
                                    sex = Dog.MALE,
                                    weight = 9.8f,
                                    owner_username = "qillaq_aapo",
                                    owner = "Qillaq Aapo",
                                    phone = "1594673850",
                                    text = "Madelief is a friendly and energetic dog looking for a loving home. He enjoys long walks in the park and playing fetch. He's great with kids and other pets. If you're looking for a loyal companion, Rex might be the perfect addition to your family.",
                                    image_urls = imageList.toTypedArray(),
                                    isFavorite = false
                                    //arrayOf("https://static.fundacion-affinity.org/cdn/farfuture/PVbbIC-0M9y4fPbbCsdvAD8bcjjtbFc0NSP3lRwlWcE/mtime:1643275542/sites/default/files/los-10-sonidos-principales-del-perro.jpg", "https://www.eltiempo.com/files/image_640_428/uploads/2023/01/05/63b73981e5a99.jpeg", "https://www.65ymas.com/uploads/s1/10/30/25/5/bigstock-421234682_1_621x621.jpeg")
                                )
                            )
                        }
                    }
                })
            }

            if(databaseHandler.insertUser(UserEntity(
                    "tove_lucian",
                    "Tove Lucian",
                    "2429218333",
                    "https://api.multiavatar.com/tove_lucian.png"
                ))) {
                apiHandler.getBreedImages("sharpei", 3, fun(images: ImagesResponse) {
                    if (images.status == ApiHandler.SUCCESS_MSG && images.message != null) {
                        var imageList = mutableListOf<String>()
                        for (image in images.message) {
                            imageList.add(image)
                        }

                        CoroutineScope(Dispatchers.IO).launch {
                            val id = databaseHandler.insertAdoption(
                                Dog(
                                    name = "Oriana",
                                    age = 8,
                                    location = Dog.Location.BUENOS_AIRES.displayName,
                                    sex = Dog.MALE,
                                    weight = 65.4f,
                                    owner_username = "tove_lucian",
                                    owner = "Tove Lucian",
                                    phone = "2429218333",
                                    text = "Oriana is a friendly and energetic dog looking for a loving home. He enjoys long walks in the park and playing fetch. He's great with kids and other pets. If you're looking for a loyal companion, Rex might be the perfect addition to your family.",
                                    image_urls = imageList.toTypedArray(),
                                    isFavorite = false
                                    //arrayOf("https://static.fundacion-affinity.org/cdn/farfuture/PVbbIC-0M9y4fPbbCsdvAD8bcjjtbFc0NSP3lRwlWcE/mtime:1643275542/sites/default/files/los-10-sonidos-principales-del-perro.jpg", "https://www.eltiempo.com/files/image_640_428/uploads/2023/01/05/63b73981e5a99.jpeg", "https://www.65ymas.com/uploads/s1/10/30/25/5/bigstock-421234682_1_621x621.jpeg")
                                )
                            )
                        }
                    }
                })
            }
        }
    }
}