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
data Light = Off | On deriving (Show, Eq)

toggle :: Light -> Light
toggle On = Off
toggle Off = On

mapSize :: Int
mapSize = 1000

start :: Vec.Vector Light
start = Vec.replicate (mapSize*mapSize) Off

main :: IO ()
main = do interact (\x -> (show . countLights . generateMap . getTree) x ++ "\n")

countLights :: Vec.Vector Light -> Int
countLights v = Vec.length $ Vec.filter (\x -> x == On) v

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

----- Part 1

generateMap :: [Node] -> Vec.Vector Light
generateMap input = foldl updateMap start input

updateMap :: Vec.Vector Light -> Node -> Vec.Vector Light
updateMap m (light, rect) = m Vec.// (map (switch light m) $ idxInside rect)

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

switch :: Command -> Vec.Vector Light -> Int -> (Int, Light)
switch TurnOn _ x = (x, On)
switch TurnOff _ x = (x, Off)
switch Toggle m x = (x, toggle (m Vec.! x))

-----
