package edu.calpoly.flipted.businesslogic


import edu.calpoly.flipted.businesslogic.tasks.GetId
import org.junit.Test
import org.junit.Assert.*


class TestURLtoID {

@Test
fun urlNonHttps() {

    val url1 = "youtu.be/1FJHYqE0RDg"
    assertEquals("1FJHYqE0RDg", GetId.Idinput.getYouTubeId(url1))

    val url2 = "youtu.be/gdevNBJZYgI"
    assertEquals("gdevNBJZYgI", GetId.Idinput.getYouTubeId(url2))

    val url3 = "youtu.be/BwtQZO0vi24"
    assertEquals("BwtQZO0vi24", GetId.Idinput.getYouTubeId(url3))

}


@Test
fun urlYoutube() {

    val url1 = "https://youtu.be/1FJHYqE0RDg"
    assertEquals("1FJHYqE0RDg", GetId.Idinput.getYouTubeId(url1))

    val url2 = "https://youtu.be/gdevNBJZYgI"
    assertEquals("gdevNBJZYgI", GetId.Idinput.getYouTubeId(url2))

    val url3 = "https://youtu.be/BwtQZO0vi24"
    assertEquals("BwtQZO0vi24", GetId.Idinput.getYouTubeId(url3))

}

@Test
fun urlWatchVEqual() {

    val url1 = "https://www.youtube.com/watch?v=R3XpjB3P9WU"
    assertEquals("R3XpjB3P9WU", GetId.Idinput.getYouTubeId(url1))

    val url2 = "https://www.youtube.com/watch?v=4AO-NBTJr5E"
    assertEquals("4AO-NBTJr5E", GetId.Idinput.getYouTubeId(url2))

    val url3 = "https://www.youtube.com/watch?v=BwtQZO0vi24"
    assertEquals("BwtQZO0vi24", GetId.Idinput.getYouTubeId(url3))

}

@Test
fun urlWatchAnd() {

    val url1 = "https://www.youtube.com/watch?v=tV36QsE7JyM&t=235s"
    assertEquals("tV36QsE7JyM", GetId.Idinput.getYouTubeId(url1))

    val url2 = "https://www.youtube.com/watch?v=4AO-NBTJr5E&t=27s"
    assertEquals("4AO-NBTJr5E", GetId.Idinput.getYouTubeId(url2))

    val url3 = "https://www.youtube.com/watch?v=R3XpjB3P9WU&t=349s"
    assertEquals("R3XpjB3P9WU", GetId.Idinput.getYouTubeId(url3))

    val url4 = "https://www.youtube.com/watch?v=fsM-rOZI3TY&list=RDfsM-rOZI3TY&start_radio=1"
    assertEquals("fsM-rOZI3TY", GetId.Idinput.getYouTubeId(url4))
}


@Test
fun urlNonYoutube() {

    val url1 = "https://en.wikipedia.org/wiki/Main_Page"
    assertEquals("Not YouTube Video", GetId.Idinput.getYouTubeId(url1))

    val url2 = "https://vimeo.com/42647970"
    assertEquals("Not YouTube Video", GetId.Idinput.getYouTubeId(url2))

}


@Test
fun JustId() {

    val url1 = "1FJHYqE0RDg"
    assertEquals("1FJHYqE0RDg", GetId.Idinput.getYouTubeId(url1))

    val url2 = "gdevNBJZYgI"
    assertEquals("gdevNBJZYgI", GetId.Idinput.getYouTubeId(url2))

    val url3 = "BwtQZO0vi24"
    assertEquals("BwtQZO0vi24", GetId.Idinput.getYouTubeId(url3))

}




}