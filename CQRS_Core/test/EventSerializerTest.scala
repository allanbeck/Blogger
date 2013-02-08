import org.scalatest.{BeforeAndAfter, FunSuite}
import org.msgpack.annotation.Message
import org.msgpack.ScalaMessagePack._

// <- import MessagePack instance for scala

/**
 * User: soren
 */
class EventSerializerTest extends FunSuite  {

  @Message
  class SampleClass {
    var name: String = "hoge"
    var number: Int = 2
  }

  test("serializer") {

    val sc = new SampleClass()
    sc.name = "Test object"
    sc.number = 123456
    // using imported method from ScalaMessagePack
    val b: Array[Byte] = write(sc) // or pack(sc) <- this is synonym.

    val deser = read[SampleClass](b) // or unpack[SampleClass](sc)

    println(deser.name)
    println(deser.number)
  }

}
