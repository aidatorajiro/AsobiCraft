from guigen import *
from PIL import *
import math

im = Image.new("RGBA", (256, 256), (255, 255, 255, 0))
draw_window(im, (253, 234), (19, 29, 47, 255), (230, 177, 223, 255), 2)

draw_slots(im, GRAY_SLOT, (174, 9), (4, 12))
draw_player_inv(im, GRAY_SLOT, (9, 151))

centerX = 89
centerY = 76
radius = 60
theta = 2*math.pi/16.0

for i in range(16):
  x = math.floor(-math.sin(theta*i)*radius + centerX - 8)
  y = math.floor(math.cos(theta*i)*radius + centerY - 8)
  if i % 2 == 0:
    im.paste(PINK_SLOT, (x, y), PINK_SLOT)
  else:
    im.paste(ORANGE_SLOT, (x, y), ORANGE_SLOT)

im.save(ASSET_DIR + "/calculator.png")
