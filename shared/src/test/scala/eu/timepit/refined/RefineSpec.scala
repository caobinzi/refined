package eu.timepit.refined

import eu.timepit.refined.char._
import eu.timepit.refined.collection._
import eu.timepit.refined.numeric._
import eu.timepit.refined.string.MatchesRegex
import org.scalacheck.Prop._
import org.scalacheck.Properties
import shapeless.nat._

class RefineSpec extends Properties("refine") {

  property("Refine instance") = secure {
    val rv = refineV[Digit]
    val rt = refineT[Digit]

    val t = '0'
    rv(t).isRight && rt(t).isRight
  }

  property("refine success with Less") = secure {
    refineV[Less[W.`100`.T]](-100).isRight &&
      refineT[Less[W.`100`.T]](-100).isRight
  }

  property("refine success with Greater") = secure {
    refineV[Greater[_5]](6).isRight &&
      refineT[Greater[_5]](6).isRight
  }

  property("refine failure with Interval") = secure {
    refineV[Interval[W.`-0.5`.T, W.`0.5`.T]](0.6).isLeft &&
      refineT[Interval[W.`-0.5`.T, W.`0.5`.T]](0.6).isLeft
  }

  property("refine failure with Forall") = secure {
    refineV[Forall[LowerCase]]("Hallo").isLeft &&
      refineT[Forall[LowerCase]]("Hallo").isLeft
  }

  property("refineT success with MatchesRegex") = secure {
    type DigitsOnly = MatchesRegex[W.`"[0-9]+"`.T]
    refineV[DigitsOnly][String]("123").isRight &&
      refineT[DigitsOnly][String]("123").isRight
  }
}
