package com.pfortbe22bgrupo2.parcialtp3.data

import com.pfortbe22bgrupo2.parcialtp3.models.Dog

class DogsList {

    var dogList: MutableList<Dog> = mutableListOf()

    init {
        dogList.add(Dog(
            id = 1,
            name = "Rex",
            age = 3,
            location = "Recoleta, Buenos Aires",
            sex = Dog.MALE,
            weight = 15.5f,
            owner_username = "jm_sarmiento",
            owner = "Juan Martin Sarmiento",
            phone = "1123478540",
            text = "Rex is a friendly and energetic dog looking for a loving home. He enjoys long walks in the park and playing fetch. He's great with kids and other pets. If you're looking for a loyal companion, Rex might be the perfect addition to your family.",
            image_urls = arrayOf("https://static.fundacion-affinity.org/cdn/farfuture/PVbbIC-0M9y4fPbbCsdvAD8bcjjtbFc0NSP3lRwlWcE/mtime:1643275542/sites/default/files/los-10-sonidos-principales-del-perro.jpg", "https://www.eltiempo.com/files/image_640_428/uploads/2023/01/05/63b73981e5a99.jpeg", "https://www.65ymas.com/uploads/s1/10/30/25/5/bigstock-421234682_1_621x621.jpeg")
        ))
        dogList.add(Dog(
            id = 2,
            name = "Clara",
            age = 2,
            location = "Núñez, Buenos Aires",
            sex = Dog.FEMALE,
            weight = 5.5f,
            owner_username = "luci_11",
            owner = "Lucía Palacios",
            phone = "1145236789",
            text = "Clara is a friendly and energetic dog looking for a loving home. She enjoys long walks in the park and playing fetch. She's great with kids and other pets. If you're looking for a loyal companion, Clara might be the perfect addition to your family.",
            image_urls = arrayOf("https://cdn.wamiz.fr/cdn-cgi/image/format=auto,quality=80,width=1200,height=675,fit=cover/article/main-picture/6391e5e6004f2924156742.jpg", "https://www.bunko.pet/__export/1638228692080/sites/debate/img/2021/11/29/cocker_spaniel_crop1638228652251.jpeg_554688468.jpeg")
        ))
        dogList.add(Dog(
            id = 3,
            name = "Hulk",
            age = 3,
            location = "San Antonio de Padua, Buenos Aires",
            sex = Dog.MALE,
            weight = 7f,
            owner_username = "messi_10",
            owner = "Lionel Messi",
            phone = "",
            text = "Hulk is a friendly and energetic dog looking for a loving home. He enjoys long walks in the park and playing fetch. He's great with kids and other pets. If you're looking for a loyal companion, Hulk might be the perfect addition to your family.",
            image_urls = arrayOf("https://www.terranea.es/assets/images/razas/dogo_de_burdeos.jpg", "https://estaticos-cdn.elperiodico.com/clip/54639c02-8fda-41dd-bc03-1e7c81a3e2fb_16-9-aspect-ratio_default_0.jpg")
        ))
        dogList.add(Dog(
            id = 4,
            name = "Frank",
            age = 2,
            location = "USA",
            sex = Dog.MALE,
            weight = 5.5f,
            owner_username = "agente_j",
            owner = "Darrell James Edwards",
            phone = "",
            text = "Frank is a friendly and energetic dog looking for a loving home. He enjoys long walks in the park and playing fetch. He's great with kids and other pets. If you're looking for a loyal companion, Frank might be the perfect addition to your family.",
            image_urls = arrayOf("https://ichef.bbci.co.uk/news/660/cpsprodpb/48DD/production/_107435681_perro1.jpg", "https://images.hola.com/imagenes/mascotas/20200831174373/razas-de-perro-mas-populares-pug-carlino/0-860-24/pug-m.jpg?tx=w_680", "https://tecolotito.elsiglodedurango.com.mx/cdn-cgi/image/format=auto,width=1024/i/2023/04/1163645.jpeg")
        ))
        dogList.add(Dog(
            id = 5,
            name = "Lucho",
            age = 4,
            location = "Moreno, Buenos Aires",
            sex = Dog.MALE,
            weight = 3.5f,
            owner_username = "marito_baraku",
            owner = "Mario Gutierrez",
            phone = "1148523679",
            text = "Lucho is a friendly and energetic dog looking for a loving home. He enjoys long walks in the park and playing fetch. He's great with kids and other pets. If you're looking for a loyal companion, Lucho might be the perfect addition to your family.",
            image_urls = arrayOf("https://www.semana.com/resizer/wb9TzO0TSIMFaezcfeSimooW3PE=/1920x0/smart/filters:format(jpg):quality(80)/cloudfront-us-east-1.images.arcpublishing.com/semana/O5OGWSXAKFDPJDV3ZVJV2ZQXEY.jpg", "https://static.abc.es/media/ciencia/2022/04/28/AdobeStock_293422760-kvsC--1200x630@abc.jpg")
        ))
    }

}