from PIL import Image, ImageDraw
import os

ASSET_DIR = os.path.dirname(os.path.abspath(__file__))

def draw_window(im, size, back_color, border_color, border_width):
  draw = ImageDraw.Draw(im)
  draw.rectangle((0, 0, size[0], size[1]), fill=back_color)
  if border_width > 0:
    draw.line((0      , 0      , 0      , size[1]), fill=border_color, width=border_width)
    draw.line((0      , size[1], size[0], size[1]), fill=border_color, width=border_width)
    draw.line((0      , 0      , size[0], 0      ), fill=border_color, width=border_width)
    draw.line((size[0], 0      , size[0], size[1]), fill=border_color, width=border_width)

def draw_slots(im, slot_im, offset, shape):
  for i in range(0, shape[0]):
    for j in range(0, shape[1]):
      x = offset[0] + i * 18;
      y = offset[1] + j * 18;
      im.paste(slot_im, (x, y), slot_im)

def draw_player_inv(im, slot_im, offset):
  draw_slots(im, slot_im, offset, (9, 3))
  draw_slots(im, slot_im, (offset[0], offset[1] + 58), (9, 1))

PINK_SLOT = Image.open(ASSET_DIR + '/pink_slot.png')
ORANGE_SLOT = Image.open(ASSET_DIR + '/orange_slot.png')
GRAY_SLOT = Image.open(ASSET_DIR + '/gray_slot.png')
