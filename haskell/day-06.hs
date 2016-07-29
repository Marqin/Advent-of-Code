{-
    This code needs "megaparsec" and "vector" packages installed.
-}

import qualified Text.Megaparsec as MP
import Text.Megaparsec.String (Parser)
import Control.Exception (throw)
import Control.Applicative ((<$>), (<|>))
import qualified Data.Vector as Vec

type Node = (Command, Rectangle)
data Command = TurnOn | TurnOff | Toggle deriving (Show, Eq)
type Rectangle = (Coord, Coord)
type Coord = (Int, Int)
type Light = Int
type SwitchFunction = Command -> Vec.Vector Light -> Int -> (Int, Light)

mapSize :: Int
mapSize = 1000

start :: Vec.Vector Light
start = Vec.replicate (mapSize*mapSize) 0

main :: IO ()
main = interact day06

day06 str = part1 ++ part2
  where
    part1 = (show . sum . (generateMap switch1) . getTree) str ++ "\n"
    part2 = (show . sum . (generateMap switch2) . getTree) str ++ "\n"

--- Parser

getTree :: String -> [Node]
getTree s =
  case (MP.parse parser "" s) of
    Left err -> throw err
    Right tree -> tree

parser :: Parser [Node]
parser = MP.sepEndBy parseLine MP.newline

parseLine :: Parser Node
parseLine =
  do command <- parseCommand
     MP.space
     rangeStart <- parseCoord
     MP.string " through "
     rangeEnd <- parseCoord
     return (command, (rangeStart, rangeEnd))

parseCommand :: Parser Command
parseCommand = (MP.string "turn on"  >> return TurnOn)
           <|> (MP.string "turn off" >> return TurnOff)
           <|> (MP.string "toggle"   >> return Toggle)

parseCoord :: Parser Coord
parseCoord =
  do x <- read <$> MP.some MP.digitChar
     MP.char ','
     y <- read <$> MP.some MP.digitChar
     return (x, y)

----- Common

generateMap :: SwitchFunction -> [Node] -> Vec.Vector Light
generateMap s input = foldl (updateMap s) start input

updateMap :: SwitchFunction -> Vec.Vector Light -> Node -> Vec.Vector Light
updateMap s m (light, rect) = m Vec.// (map (s light m) $ idxInside rect)

idxInside :: Rectangle -> [Int]
idxInside x = map idx $ pointsInside x

pointsInside :: Rectangle -> [(Int, Int)]
pointsInside ((x1, y1), (x2, y2)) =
  [(x, y) | x <- [x_min..x_max], y <- [y_min..y_max] ]
    where
      x_min = minimum [x1, x2]
      x_max = maximum [x1, x2]
      y_min = minimum [y1, y2]
      y_max = maximum [y1, y2]

idx :: (Int, Int) -> Int
idx (x,y) = x + mapSize * y

toggle :: Light -> Light
toggle 1 = 0
toggle 0 = 1

----- Part 1

switch1 :: SwitchFunction
switch1 TurnOn _ x = (x, 1)
switch1 TurnOff _ x = (x, 0)
switch1 Toggle m x = (x, toggle (m Vec.! x))

----- Part 2

switch2 :: SwitchFunction
switch2 TurnOn m x = (x, (m Vec.! x) + 1)
switch2 TurnOff m x = (x, decrease m x)
switch2 Toggle m x = (x, (m Vec.! x) + 2)

decrease :: Vec.Vector Light -> Int -> Int
decrease m x
  | val < 1 = 0
  | otherwise = val - 1
  where val = (m Vec.! x)
