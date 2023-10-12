export const DEFAULT_STYLE = 'Реализм';
export const DEFAULT_AI_SYSTEM = 'STABLE_DIFFUSION';
export const DEFAULT_MODEL = 'epicrealismnaturalsi';
export const DEFAULT_STYLE_DESCRIPTION = 'Realism style, Realistic representation of subjects, lifelike details';
export const DEFAULT_ASPECT_RATIO = '1 x 1 (512:512)';
export const DEFAULT_WIDTH_HEIGHT = '512:512';
export const DEFAULT_NUM_INFERENCE_STEP = '31';
export const DEFAULT_CLIP_SKIP = "2";
export const DEFAULT_NEGATIVE_PROMPT = "(child:1.5), ((((underage)))), ((((child)))), (((kid))), (((preteen))), (teen:1.5) ugly, tiling, poorly drawn hands, poorly drawn feet, poorly drawn face, out of frame, extra limbs, disfigured, deformed, body out of frame, bad anatomy, watermark, signature, cut off, low contrast, underexposed, overexposed, bad art, beginner, amateur, distorted face, blurry, draft, grainy, bad anatomy, ugly representations, badly drawn elements, broken arms, mutations, mutilations, deformations, unexpected finger\\\\\\/limb counts, endorse hate speech, bullying, harassment, violate copyrights, trademarks, none clear, low-quality images, lowres, bad anatomy, bad hands, cropped, worst quality, ugly, deform, disfigured";

export const DEFAULT_PROMPT = 'Here will be generated prompt text';
export const DEFAULT_PROMPT_REQUEST = 'Here will be prompt request';
export const DEFAULT_CONCEPT = 'Here will be website concept';
export const DEFAULT_IMAGE = 'https://cdn3.vectorstock.com/i/1000x1000/35/52/placeholder-rgb-color-icon-vector-32173552.jpg'

export const STYLES_MODELS_MAP = [
  {
    style: "Реализм (realistic-vision-v51)",
    aiSystem: "STABLE_DIFFUSION",
    model: "realistic-vision-v51",
    description: "(RAW photo, subject, 8k uhd, dslr, soft lighting, high quality, film grain, Fujifilm XT3) " +
        "Realism style, Realistic representation of subjects, lifelike details"
  },
  {
    style: "Реализм (epicrealismnaturalsi)",
    aiSystem: "STABLE_DIFFUSION",
    model: "epicrealismnaturalsi",
    description: "Realism style, Realistic representation of subjects, lifelike details"
  },
  {
    style: "Рекламный (zovya111)",
    aiSystem: "STABLE_DIFFUSION",
    model: "zovya111",
    description: "Advertising style, Commercial and promotional visuals, bright and attention-grabbing"
  },
  {
    style: "Киностиль (epicrealism-v1)",
    aiSystem: "STABLE_DIFFUSION",
    model: "epicrealism-v1",
    description: "Cinematic style, Evoking emotions and scenes typical of movies"
  },
  {
    style: "Архитектура (xsarchitectural-inte)",
    aiSystem: "STABLE_DIFFUSION",
    model: "xsarchitectural-inte",
    description: "Architecture style, Structures, buildings, and design forms"
  },
  {
    style: "Индустриальный дизайн (techrealistic)",
    aiSystem: "STABLE_DIFFUSION",
    model: "techrealistic",
    description: "Industrial design style, Functional design associated with machinery and industry aesthetics"
  },
  {
    style: "3d анимация (samaritan-3d-cartoon)",
    aiSystem: "STABLE_DIFFUSION",
    model: "samaritan-3d-cartoon",
    description: "3D animation style, Three-dimensional animated visuals"
  },
  {
    style: "Мультипликация (sdvn5-3dcutewave)",
    aiSystem: "STABLE_DIFFUSION",
    model: "sdvn5-3dcutewave",
    description: "Animation style, Animated sequences, often colorful and lively"
  },
  {
    style: "Leonardo test (6bef9f1b-29cb-40c7-b9df-32b51c1f67d3)",
    aiSystem: "LEONARDO",
    model: "6bef9f1b-29cb-40c7-b9df-32b51c1f67d3",
    description: "Realistic miracle"
  },
  {
    style: "Leonardo photoreal (photoreal)",
    aiSystem: "LEONARDO",
    model: "photoreal",
    description: "CINEMATIC"
  }
];

export const ASPECT_RATIO_MAP = [
  {
    view: "1 x 1 (512:512)",
    height: "512",
    width: "512",
    ratioFormat: "square"
  },
  {
    view: "1 x 1 (768:768)",
    height: "768",
    width: "768",
    ratioFormat: "square"
  },
  {
    view: "1 x 1 (1024:1024)",
    height: "1024",
    width: "1024",
    ratioFormat: "square"
  },
  {
    view: "16 x 9 (1024:576)",
    width: "1024",
    height: "576",
    ratioFormat: "horizontal"
  },
  {
    view: "145 x 85 (1024:600)",
    width: "1024",
    height: "600",
    ratioFormat: "horizontal"
  },
  {
    view: "560 x 315 (1024:576)",
    width: "1024",
    height: "576",
    ratioFormat: "horizontal"
  }
]

export const NUM_INFERENCE_STEPS = ["21","31","41","51"]
