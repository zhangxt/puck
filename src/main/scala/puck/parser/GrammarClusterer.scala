package puck.parser

import epic.trees.BinaryRule
import scala.collection.immutable.BitSet
import puck.parser.GrammarClusterer._
import scala.collection.immutable

/**
 * TODO
 *
 * @author dlwh
 **/
trait GrammarClusterer extends Serializable {
  def partition[C, L](rules: IndexedSeq[(BinaryRule[SymId[C, L]], Int)],
                targetLabel: TargetLabel = Parent): IndexedSeq[immutable.IndexedSeq[(BinaryRule[SymId[C, L]], Int)]]
}

object GrammarClusterer {
  sealed trait TargetLabel {
    def clusterPieces[C, L](r: BinaryRule[SymId[C, L]]) = this match {
      case Parent => BitSet(r.left.gpu) -> BitSet(r.right.gpu)
      case LeftChild => BitSet(r.parent.gpu) -> BitSet(r.right.gpu)
      case RightChild => BitSet(r.parent.gpu) -> BitSet(r.left.gpu)
    }

    def target[C, L](r: BinaryRule[SymId[C, L]]) = this match {
      case Parent => r.parent.gpu
      case LeftChild => r.left.gpu
      case RightChild => r.right.gpu
    }
  }
  case object Parent extends TargetLabel
  case object LeftChild extends TargetLabel
  case object RightChild extends TargetLabel



}

