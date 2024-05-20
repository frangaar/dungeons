package mazmorra;

import mazmorra.characters.Priest;
import mazmorra.items.Skull;
import mazmorra.characters.Warrior;
import mazmorra.characters.Wizard;
import mazmorra.items.*;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.*;

public class Game{

    private JFrame frame = new JFrame("Game");
    private JPanel scenarioPanel;
    private JPanel scorePanel = new JPanel();
    private JPanel itemsPanel = new JPanel();
    private JPanel controlsPanel = new JPanel();
    private JPanel gamePanel = new JPanel();

    private String IMG_CARA_PARADO, IMG_ESPALDA_PARADO, IMG_DERECHA_PARADO, IMG_IZQUIERDA_PARADO = "";
    private String IMG_CARA_MOVIMIENTO, IMG_ESPALDA_MOVIMIENTO, IMG_DERECHA_MOVIMIENTO, IMG_IZQUIERDA_MOVIMIENTO = "";

    private String IMG_GOLD = "img/dungeon/dollar.gif";
    private final String IMG_SUEL_P1 = "img/dungeon/terra.png";
//    private final String IMG_MURO = "img/dungeon/mur.png";
    private final String IMG_MURO_H1 = "img/dungeon/murH1.png";
    private final String IMG_MURO_H2 = "img/dungeon/murH2.png";
    private final String IMG_ESPASA = "img/dungeon/sword.png";
    private final String IMG_POCIO = "img/dungeon/potion.png";
    private final String IMG_MITRA = "img/dungeon/mitra.png";
    private final String IMG_COR = "img/dungeon/heart.png";

    private final String IMG_SKULL_UP = "img/skeleton/skeleton_up.gif";
    private final String IMG_SKULL_RIGHT = "img/skeleton/skeleton_right.gif";
    private final String IMG_SKULL_DOWN = "img/skeleton/skeleton_down.gif";
    private final String IMG_SKULL_LEFT = "img/skeleton/skeleton_left.gif";

    private String IMG_RIGHT = "";
    private String IMG_LEFT = "";
    private String IMG_UP = "";
    private String IMG_DOWN = "";

    private boolean left = false;
    private boolean up = false;
    private boolean down = false;
    private boolean right = false;


    private final int MIN_GOLD = 50;

    private final int ANCHO_LABELS = 32, ALTO_LABELS = 32;
    private final int ANCHO_CHARACTERS = 35, ALTO_CHARACTERS = 48;

    private int fireX,fireY;
    private String direction = "down";

    private int shots = 0;
    private String playerType = "";

    private URL urlPlayer;
    private ImageIcon iconPlayer;

    private JLabel lblTime = new JLabel("Game time: ");
    private int seconds = 30;
    private boolean timeChecked = false;
    private JLabel lblTimeCounter = new JLabel(seconds + " sec");
    private JLabel lblControls = new JLabel();
    private JLabel lblPlayer = new JLabel();

    private JLabel lblPoisoned = new JLabel();
    private JLabel lblPlayerInfo = new JLabel("Player Info:");
    private JLabel iconName = new JLabel();
    private JLabel lblName = new JLabel();
    private JLabel iconLifes = new JLabel();
    private JLabel lblLifes = new JLabel();
    private JLabel iconGold = new JLabel();
    private JLabel lblGoldInfo = new JLabel("0");
    private JLabel lblItemsList = new JLabel("Items List:");

    // Dungeon items
    private JLabel itemHeart = new JLabel();
    private JLabel itemPotion = new JLabel();
    private JLabel itemMitra = new JLabel();
    private JLabel itemSword = new JLabel();
    private JLabel itemCoins = new JLabel();

    private int gold;

    private final String purple = "#59377a";
    private final String red = "#E01A2F";

    private Character player;
    private JProgressBar healthPlayer = new JProgressBar(0,500);

    private boolean poisoned;
    private final int DEFAULT_LIFE = 1000;

    private Icon icon;

    private int anchura;

    private final int DUNGEON_ITEM_WIDTH = 32;
    private final int DUNGEON_ITEM_HEIGHT = 32;

    private final String RANKING =  "src/mazmorra/ranking/ranking.txt";
    private Date horaInicio;
    private int duration;
    private int refreshTime = 0;

    private Timer timeChecker;
    private Timer proceed;

    private boolean choque = false, antesX = false, despuesX = false, antesY = false, despuesY = false;

    private Terra sueloItem = new Terra("terra",null , null, null,"", IMG_SUEL_P1, DUNGEON_ITEM_WIDTH, DUNGEON_ITEM_HEIGHT,0,0,0,0);
    private Mur   paredItem = new Mur("mur",null , null, null,"", IMG_MURO_H1, DUNGEON_ITEM_WIDTH, DUNGEON_ITEM_HEIGHT,0,0,0,0);
    private Gold goldItem;
    private Sword swordItem;
    private Potion potionItem;
    private Mitra mitraItem;

    private Map<JLabel,Timer> fireBalls = new HashMap<>();
    private ArrayList<JLabel> skullsList = new ArrayList<>();
    private ArrayList<Skull> skullsObjList = new ArrayList<>();

    private boolean spacePressed = false;
    private boolean ctrlPressed = false;


//    private JLabel items[] = {
//            paredItem.create(100),paredItem.create(101),paredItem.create(102),paredItem.create(103),paredItem.create(104),paredItem.create(105),paredItem.create(106),paredItem.create(107),paredItem.create(108),paredItem.create(109),paredItem.create(110),paredItem.create(111),paredItem.create(112),paredItem.create(113),paredItem.create(114),paredItem.create(115),paredItem.create(116),paredItem.create(117),paredItem.create(118),paredItem.create(119),paredItem.create(120),paredItem.create(121),paredItem.create(122),paredItem.create(123),paredItem.create(124),paredItem.create(125),paredItem.create(126),paredItem.create(127),paredItem.create(128),paredItem.create(129),paredItem.create(130),paredItem.create(131),paredItem.create(132),paredItem.create(133),paredItem.create(134),paredItem.create(135),paredItem.create(136),
//            sueloItem.create(137),sueloItem.create(138),sueloItem.create(139),sueloItem.create(140),sueloItem.create(141),sueloItem.create(142),sueloItem.create(143),sueloItem.create(144),sueloItem.create(145),sueloItem.create(146),sueloItem.create(147),sueloItem.create(148),sueloItem.create(149),sueloItem.create(150),sueloItem.create(151),sueloItem.create(152),sueloItem.create(153),sueloItem.create(154),sueloItem.create(155),sueloItem.create(156),sueloItem.create(157),sueloItem.create(158),sueloItem.create(159),sueloItem.create(160),sueloItem.create(161),sueloItem.create(162),sueloItem.create(163),sueloItem.create(164),sueloItem.create(165),sueloItem.create(166),sueloItem.create(167),sueloItem.create(168),sueloItem.create(169),sueloItem.create(170),sueloItem.create(171),sueloItem.create(172),paredItem.create(173),
//            sueloItem.create(174),sueloItem.create(175),sueloItem.create(176),sueloItem.create(177),sueloItem.create(178),sueloItem.create(179),sueloItem.create(180),sueloItem.create(181),sueloItem.create(182),sueloItem.create(183),sueloItem.create(184),sueloItem.create(185),sueloItem.create(186),sueloItem.create(187),sueloItem.create(188),sueloItem.create(189),sueloItem.create(190),sueloItem.create(191),sueloItem.create(192),sueloItem.create(193),sueloItem.create(194),sueloItem.create(195),sueloItem.create(196),sueloItem.create(197),sueloItem.create(198),sueloItem.create(199),sueloItem.create(100),sueloItem.create(101),sueloItem.create(102),sueloItem.create(103),sueloItem.create(104),sueloItem.create(105),sueloItem.create(106),sueloItem.create(107),sueloItem.create(108),sueloItem.create(109),paredItem.create(110),
//            paredItem.create(111),sueloItem.create(112),sueloItem.create(113),sueloItem.create(114),sueloItem.create(115),sueloItem.create(116),sueloItem.create(117),sueloItem.create(118),sueloItem.create(119),sueloItem.create(120),sueloItem.create(121),sueloItem.create(122),sueloItem.create(123),sueloItem.create(124),sueloItem.create(125),sueloItem.create(126),sueloItem.create(127),sueloItem.create(128),sueloItem.create(129),sueloItem.create(130),sueloItem.create(131),sueloItem.create(132),sueloItem.create(133),sueloItem.create(134),sueloItem.create(135),sueloItem.create(136),sueloItem.create(137),sueloItem.create(138),sueloItem.create(139),sueloItem.create(140),sueloItem.create(141),sueloItem.create(142),sueloItem.create(143),sueloItem.create(144),sueloItem.create(145),sueloItem.create(146),paredItem.create(147),
//            paredItem.create(148),sueloItem.create(149),sueloItem.create(150),sueloItem.create(151),sueloItem.create(152),sueloItem.create(153),sueloItem.create(154),sueloItem.create(155),sueloItem.create(156),sueloItem.create(157),sueloItem.create(158),sueloItem.create(159),sueloItem.create(160),sueloItem.create(161),sueloItem.create(162),sueloItem.create(163),sueloItem.create(164),sueloItem.create(165),sueloItem.create(166),sueloItem.create(167),sueloItem.create(168),sueloItem.create(169),sueloItem.create(170),sueloItem.create(171),sueloItem.create(172),sueloItem.create(173),sueloItem.create(174),sueloItem.create(175),sueloItem.create(176),sueloItem.create(177),sueloItem.create(178),sueloItem.create(179),sueloItem.create(180),sueloItem.create(181),sueloItem.create(182),sueloItem.create(183),paredItem.create(184),
//            paredItem.create(185),sueloItem.create(186),sueloItem.create(187),sueloItem.create(188),sueloItem.create(189),sueloItem.create(190),sueloItem.create(191),sueloItem.create(192),sueloItem.create(193),sueloItem.create(194),sueloItem.create(195),sueloItem.create(196),sueloItem.create(197),sueloItem.create(198),sueloItem.create(199),sueloItem.create(200),sueloItem.create(201),sueloItem.create(202),sueloItem.create(203),sueloItem.create(204),sueloItem.create(205),sueloItem.create(206),sueloItem.create(207),sueloItem.create(208),sueloItem.create(209),sueloItem.create(210),sueloItem.create(211),sueloItem.create(212),sueloItem.create(213),sueloItem.create(214),sueloItem.create(215),sueloItem.create(216),sueloItem.create(217),sueloItem.create(218),sueloItem.create(219),sueloItem.create(220),paredItem.create(221),
//            paredItem.create(222),sueloItem.create(223),sueloItem.create(224),sueloItem.create(225),sueloItem.create(226),sueloItem.create(227),sueloItem.create(228),sueloItem.create(229),sueloItem.create(230),sueloItem.create(231),sueloItem.create(232),sueloItem.create(233),sueloItem.create(234),sueloItem.create(235),sueloItem.create(236),sueloItem.create(237),sueloItem.create(238),sueloItem.create(239),sueloItem.create(240),sueloItem.create(241),sueloItem.create(242),sueloItem.create(243),sueloItem.create(244),sueloItem.create(245),sueloItem.create(246),sueloItem.create(247),sueloItem.create(248),sueloItem.create(249),sueloItem.create(250),sueloItem.create(251),sueloItem.create(252),sueloItem.create(253),sueloItem.create(254),sueloItem.create(255),sueloItem.create(256),sueloItem.create(257),paredItem.create(258),
//            paredItem.create(259),sueloItem.create(260),sueloItem.create(261),sueloItem.create(262),sueloItem.create(263),sueloItem.create(264),sueloItem.create(265),sueloItem.create(266),sueloItem.create(267),sueloItem.create(268),sueloItem.create(269),sueloItem.create(270),sueloItem.create(271),sueloItem.create(272),sueloItem.create(273),sueloItem.create(274),sueloItem.create(275),sueloItem.create(276),sueloItem.create(277),sueloItem.create(278),sueloItem.create(279),sueloItem.create(280),sueloItem.create(281),sueloItem.create(282),sueloItem.create(283),sueloItem.create(284),sueloItem.create(285),sueloItem.create(286),sueloItem.create(287),sueloItem.create(288),sueloItem.create(289),sueloItem.create(290),sueloItem.create(291),sueloItem.create(292),sueloItem.create(293),sueloItem.create(294),paredItem.create(295),
//            paredItem.create(296),sueloItem.create(297),sueloItem.create(298),sueloItem.create(299),sueloItem.create(300),sueloItem.create(301),sueloItem.create(302),sueloItem.create(303),sueloItem.create(304),sueloItem.create(305),sueloItem.create(306),sueloItem.create(307),sueloItem.create(308),sueloItem.create(309),sueloItem.create(310),sueloItem.create(311),sueloItem.create(312),sueloItem.create(313),sueloItem.create(314),sueloItem.create(315),sueloItem.create(316),sueloItem.create(317),sueloItem.create(318),sueloItem.create(319),sueloItem.create(320),sueloItem.create(321),sueloItem.create(322),sueloItem.create(323),sueloItem.create(324),sueloItem.create(325),sueloItem.create(326),sueloItem.create(327),sueloItem.create(328),sueloItem.create(329),sueloItem.create(330),sueloItem.create(331),paredItem.create(332),
//            paredItem.create(333),sueloItem.create(334),sueloItem.create(335),sueloItem.create(336),sueloItem.create(337),sueloItem.create(338),sueloItem.create(339),sueloItem.create(340),sueloItem.create(341),sueloItem.create(342),sueloItem.create(343),sueloItem.create(344),sueloItem.create(345),sueloItem.create(346),sueloItem.create(347),sueloItem.create(348),sueloItem.create(349),sueloItem.create(350),sueloItem.create(351),sueloItem.create(352),sueloItem.create(353),sueloItem.create(354),sueloItem.create(355),sueloItem.create(356),sueloItem.create(357),sueloItem.create(358),sueloItem.create(359),sueloItem.create(360),sueloItem.create(361),sueloItem.create(362),sueloItem.create(363),sueloItem.create(364),sueloItem.create(365),sueloItem.create(366),sueloItem.create(367),sueloItem.create(368),paredItem.create(369),
//            paredItem.create(370),sueloItem.create(371),sueloItem.create(372),sueloItem.create(373),sueloItem.create(374),sueloItem.create(375),sueloItem.create(376),sueloItem.create(377),sueloItem.create(378),sueloItem.create(379),sueloItem.create(380),sueloItem.create(381),sueloItem.create(382),sueloItem.create(383),sueloItem.create(384),sueloItem.create(385),sueloItem.create(386),sueloItem.create(387),sueloItem.create(388),sueloItem.create(389),sueloItem.create(390),sueloItem.create(391),sueloItem.create(392),sueloItem.create(393),sueloItem.create(394),sueloItem.create(395),sueloItem.create(396),sueloItem.create(397),sueloItem.create(398),sueloItem.create(399),sueloItem.create(400),sueloItem.create(401),sueloItem.create(402),sueloItem.create(403),sueloItem.create(404),sueloItem.create(405),paredItem.create(406),
//            paredItem.create(407),sueloItem.create(408),sueloItem.create(409),sueloItem.create(410),sueloItem.create(411),sueloItem.create(412),sueloItem.create(413),sueloItem.create(414),sueloItem.create(415),sueloItem.create(416),sueloItem.create(417),sueloItem.create(418),sueloItem.create(419),sueloItem.create(420),sueloItem.create(421),sueloItem.create(422),sueloItem.create(423),sueloItem.create(424),sueloItem.create(425),sueloItem.create(426),sueloItem.create(427),sueloItem.create(428),sueloItem.create(429),sueloItem.create(430),sueloItem.create(431),sueloItem.create(432),sueloItem.create(433),sueloItem.create(434),sueloItem.create(435),sueloItem.create(436),sueloItem.create(437),sueloItem.create(438),sueloItem.create(439),sueloItem.create(440),sueloItem.create(441),sueloItem.create(442),paredItem.create(443),
//            paredItem.create(444),sueloItem.create(445),sueloItem.create(446),sueloItem.create(447),sueloItem.create(448),sueloItem.create(449),sueloItem.create(450),sueloItem.create(451),sueloItem.create(452),sueloItem.create(453),sueloItem.create(454),sueloItem.create(455),sueloItem.create(456),sueloItem.create(457),sueloItem.create(458),sueloItem.create(459),sueloItem.create(460),sueloItem.create(461),sueloItem.create(462),sueloItem.create(463),sueloItem.create(464),sueloItem.create(465),sueloItem.create(466),sueloItem.create(467),sueloItem.create(468),sueloItem.create(469),sueloItem.create(470),sueloItem.create(471),sueloItem.create(472),sueloItem.create(473),sueloItem.create(474),sueloItem.create(475),sueloItem.create(476),sueloItem.create(477),sueloItem.create(478),sueloItem.create(479),paredItem.create(480),
//            paredItem.create(481),sueloItem.create(482),sueloItem.create(483),sueloItem.create(484),sueloItem.create(485),sueloItem.create(486),sueloItem.create(487),sueloItem.create(488),sueloItem.create(489),sueloItem.create(490),sueloItem.create(491),sueloItem.create(492),sueloItem.create(493),sueloItem.create(494),sueloItem.create(495),sueloItem.create(496),sueloItem.create(497),sueloItem.create(498),sueloItem.create(499),sueloItem.create(500),sueloItem.create(501),sueloItem.create(502),sueloItem.create(503),sueloItem.create(504),sueloItem.create(505),sueloItem.create(506),sueloItem.create(507),sueloItem.create(508),sueloItem.create(509),sueloItem.create(510),sueloItem.create(511),sueloItem.create(512),sueloItem.create(513),sueloItem.create(514),sueloItem.create(515),sueloItem.create(516),paredItem.create(517),
//            paredItem.create(518),sueloItem.create(519),sueloItem.create(520),sueloItem.create(521),sueloItem.create(522),sueloItem.create(523),sueloItem.create(524),sueloItem.create(525),sueloItem.create(526),sueloItem.create(527),sueloItem.create(528),sueloItem.create(529),sueloItem.create(530),sueloItem.create(531),sueloItem.create(532),sueloItem.create(533),sueloItem.create(534),sueloItem.create(535),sueloItem.create(536),sueloItem.create(537),sueloItem.create(538),sueloItem.create(539),sueloItem.create(540),sueloItem.create(541),sueloItem.create(542),sueloItem.create(543),sueloItem.create(544),sueloItem.create(545),sueloItem.create(546),sueloItem.create(547),sueloItem.create(548),sueloItem.create(549),sueloItem.create(550),sueloItem.create(551),sueloItem.create(552),sueloItem.create(553),paredItem.create(554),
//            paredItem.create(555),sueloItem.create(556),sueloItem.create(557),sueloItem.create(558),sueloItem.create(559),sueloItem.create(560),sueloItem.create(561),sueloItem.create(562),sueloItem.create(563),sueloItem.create(564),sueloItem.create(565),sueloItem.create(566),sueloItem.create(567),sueloItem.create(568),sueloItem.create(569),sueloItem.create(570),sueloItem.create(571),sueloItem.create(572),sueloItem.create(573),sueloItem.create(574),sueloItem.create(575),sueloItem.create(576),sueloItem.create(577),sueloItem.create(578),sueloItem.create(579),sueloItem.create(580),sueloItem.create(581),sueloItem.create(582),sueloItem.create(583),sueloItem.create(584),sueloItem.create(585),sueloItem.create(586),sueloItem.create(587),sueloItem.create(588),sueloItem.create(589),sueloItem.create(590),paredItem.create(591),
//            paredItem.create(592),sueloItem.create(593),sueloItem.create(594),sueloItem.create(595),sueloItem.create(596),sueloItem.create(597),sueloItem.create(598),sueloItem.create(599),sueloItem.create(600),sueloItem.create(601),sueloItem.create(602),sueloItem.create(603),sueloItem.create(604),sueloItem.create(605),sueloItem.create(606),sueloItem.create(607),sueloItem.create(608),sueloItem.create(609),sueloItem.create(610),sueloItem.create(611),sueloItem.create(612),sueloItem.create(613),sueloItem.create(614),sueloItem.create(615),sueloItem.create(616),sueloItem.create(617),sueloItem.create(618),sueloItem.create(619),sueloItem.create(620),sueloItem.create(621),sueloItem.create(622),sueloItem.create(623),sueloItem.create(624),sueloItem.create(625),sueloItem.create(626),sueloItem.create(627),paredItem.create(628),
//            paredItem.create(629),sueloItem.create(630),sueloItem.create(631),sueloItem.create(632),sueloItem.create(633),sueloItem.create(634),sueloItem.create(635),sueloItem.create(636),sueloItem.create(637),sueloItem.create(638),sueloItem.create(639),sueloItem.create(640),sueloItem.create(641),sueloItem.create(642),sueloItem.create(643),sueloItem.create(644),sueloItem.create(645),sueloItem.create(646),sueloItem.create(647),sueloItem.create(648),sueloItem.create(649),sueloItem.create(650),sueloItem.create(651),sueloItem.create(652),sueloItem.create(653),sueloItem.create(654),sueloItem.create(655),sueloItem.create(656),sueloItem.create(657),sueloItem.create(658),sueloItem.create(659),sueloItem.create(660),sueloItem.create(661),sueloItem.create(662),sueloItem.create(663),sueloItem.create(664),paredItem.create(665),
//            paredItem.create(666),paredItem.create(667),paredItem.create(668),paredItem.create(669),paredItem.create(670),paredItem.create(671),paredItem.create(672),paredItem.create(673),paredItem.create(674),paredItem.create(675),paredItem.create(676),paredItem.create(677),paredItem.create(678),paredItem.create(679),paredItem.create(680),paredItem.create(681),paredItem.create(682),paredItem.create(683),paredItem.create(684),paredItem.create(685),paredItem.create(686),paredItem.create(687),paredItem.create(688),paredItem.create(689),paredItem.create(690),paredItem.create(691),paredItem.create(692),paredItem.create(693),paredItem.create(694),paredItem.create(695),paredItem.create(696),paredItem.create(697),paredItem.create(698),paredItem.create(699),paredItem.create(700),paredItem.create(701),paredItem.create(702)
//            };

    private JLabel items[] = {
            paredItem.create(100,IMG_MURO_H1),paredItem.create(101,IMG_MURO_H1),paredItem.create(102,IMG_MURO_H1),paredItem.create(103,IMG_MURO_H1),paredItem.create(104,IMG_MURO_H1),paredItem.create(105,IMG_MURO_H1),paredItem.create(106,IMG_MURO_H1),paredItem.create(107,IMG_MURO_H1),paredItem.create(108,IMG_MURO_H1),paredItem.create(109,IMG_MURO_H1),paredItem.create(110,IMG_MURO_H1),paredItem.create(111,IMG_MURO_H1),paredItem.create(112,IMG_MURO_H1),paredItem.create(113,IMG_MURO_H1),paredItem.create(114,IMG_MURO_H1),paredItem.create(115,IMG_MURO_H1),paredItem.create(116,IMG_MURO_H1),paredItem.create(117,IMG_MURO_H1),paredItem.create(118,IMG_MURO_H1),paredItem.create(119,IMG_MURO_H1),paredItem.create(120,IMG_MURO_H1),paredItem.create(121,IMG_MURO_H1),paredItem.create(122,IMG_MURO_H1),paredItem.create(123,IMG_MURO_H1),paredItem.create(124,IMG_MURO_H1),paredItem.create(125,IMG_MURO_H1),paredItem.create(126,IMG_MURO_H1),paredItem.create(127,IMG_MURO_H1),paredItem.create(128,IMG_MURO_H1),paredItem.create(129,IMG_MURO_H1),paredItem.create(130,IMG_MURO_H1),paredItem.create(131,IMG_MURO_H1),paredItem.create(132,IMG_MURO_H1),paredItem.create(133,IMG_MURO_H1),paredItem.create(134,IMG_MURO_H1),paredItem.create(135,IMG_MURO_H1),paredItem.create(136,IMG_MURO_H1),
            paredItem.create(137,IMG_MURO_H2),paredItem.create(138,IMG_MURO_H2),paredItem.create(139,IMG_MURO_H2),paredItem.create(140,IMG_MURO_H2),paredItem.create(141,IMG_MURO_H2),paredItem.create(142,IMG_MURO_H2),paredItem.create(143,IMG_MURO_H2),paredItem.create(144,IMG_MURO_H2),paredItem.create(145,IMG_MURO_H2),paredItem.create(146,IMG_MURO_H2),paredItem.create(147,IMG_MURO_H2),paredItem.create(148,IMG_MURO_H2),paredItem.create(149,IMG_MURO_H2),paredItem.create(150,IMG_MURO_H2),paredItem.create(151,IMG_MURO_H2),paredItem.create(152,IMG_MURO_H2),paredItem.create(153,IMG_MURO_H2),paredItem.create(154,IMG_MURO_H2),paredItem.create(155,IMG_MURO_H2),paredItem.create(156,IMG_MURO_H2),paredItem.create(157,IMG_MURO_H2),paredItem.create(158,IMG_MURO_H2),paredItem.create(159,IMG_MURO_H2),paredItem.create(160,IMG_MURO_H2),paredItem.create(161,IMG_MURO_H2),paredItem.create(162,IMG_MURO_H2),paredItem.create(163,IMG_MURO_H2),paredItem.create(164,IMG_MURO_H2),paredItem.create(165,IMG_MURO_H2),paredItem.create(166,IMG_MURO_H2),paredItem.create(167,IMG_MURO_H2),paredItem.create(168,IMG_MURO_H2),paredItem.create(169,IMG_MURO_H2),paredItem.create(170,IMG_MURO_H2),paredItem.create(171,IMG_MURO_H2),paredItem.create(172,IMG_MURO_H2),paredItem.create(173,IMG_MURO_H1),
            sueloItem.create(174,IMG_SUEL_P1),sueloItem.create(175,IMG_SUEL_P1),sueloItem.create(176,IMG_SUEL_P1),sueloItem.create(177,IMG_SUEL_P1),sueloItem.create(178,IMG_SUEL_P1),sueloItem.create(179,IMG_SUEL_P1),sueloItem.create(180,IMG_SUEL_P1),sueloItem.create(181,IMG_SUEL_P1),sueloItem.create(182,IMG_SUEL_P1),sueloItem.create(183,IMG_SUEL_P1),sueloItem.create(184,IMG_SUEL_P1),sueloItem.create(185,IMG_SUEL_P1),sueloItem.create(186,IMG_SUEL_P1),sueloItem.create(187,IMG_SUEL_P1),sueloItem.create(188,IMG_SUEL_P1),sueloItem.create(189,IMG_SUEL_P1),sueloItem.create(190,IMG_SUEL_P1),sueloItem.create(191,IMG_SUEL_P1),sueloItem.create(192,IMG_SUEL_P1),sueloItem.create(193,IMG_SUEL_P1),sueloItem.create(194,IMG_SUEL_P1),sueloItem.create(195,IMG_SUEL_P1),sueloItem.create(196,IMG_SUEL_P1),sueloItem.create(197,IMG_SUEL_P1),sueloItem.create(198,IMG_SUEL_P1),sueloItem.create(199,IMG_SUEL_P1),sueloItem.create(200,IMG_SUEL_P1),sueloItem.create(201,IMG_SUEL_P1),sueloItem.create(202,IMG_SUEL_P1),sueloItem.create(203,IMG_SUEL_P1),sueloItem.create(204,IMG_SUEL_P1),sueloItem.create(205,IMG_SUEL_P1),sueloItem.create(206,IMG_SUEL_P1),sueloItem.create(207,IMG_SUEL_P1),sueloItem.create(208,IMG_SUEL_P1),sueloItem.create(209,IMG_SUEL_P1),paredItem.create(210,IMG_MURO_H1),
            sueloItem.create(211,IMG_SUEL_P1),sueloItem.create(212,IMG_SUEL_P1),sueloItem.create(213,IMG_SUEL_P1),sueloItem.create(214,IMG_SUEL_P1),sueloItem.create(215,IMG_SUEL_P1),sueloItem.create(216,IMG_SUEL_P1),sueloItem.create(217,IMG_SUEL_P1),sueloItem.create(218,IMG_SUEL_P1),sueloItem.create(219,IMG_SUEL_P1),sueloItem.create(220,IMG_SUEL_P1),sueloItem.create(221,IMG_SUEL_P1),sueloItem.create(222,IMG_SUEL_P1),sueloItem.create(223,IMG_SUEL_P1),sueloItem.create(224,IMG_SUEL_P1),sueloItem.create(225,IMG_SUEL_P1),sueloItem.create(226,IMG_SUEL_P1),sueloItem.create(227,IMG_SUEL_P1),sueloItem.create(228,IMG_SUEL_P1),sueloItem.create(229,IMG_SUEL_P1),sueloItem.create(230,IMG_SUEL_P1),sueloItem.create(231,IMG_SUEL_P1),sueloItem.create(232,IMG_SUEL_P1),sueloItem.create(233,IMG_SUEL_P1),sueloItem.create(234,IMG_SUEL_P1),sueloItem.create(235,IMG_SUEL_P1),sueloItem.create(236,IMG_SUEL_P1),sueloItem.create(237,IMG_SUEL_P1),sueloItem.create(238,IMG_SUEL_P1),sueloItem.create(239,IMG_SUEL_P1),sueloItem.create(240,IMG_SUEL_P1),sueloItem.create(241,IMG_SUEL_P1),sueloItem.create(242,IMG_SUEL_P1),sueloItem.create(243,IMG_SUEL_P1),sueloItem.create(244,IMG_SUEL_P1),sueloItem.create(245,IMG_SUEL_P1),sueloItem.create(246,IMG_SUEL_P1),paredItem.create(247,IMG_MURO_H1),
            paredItem.create(248,IMG_MURO_H1),sueloItem.create(249,IMG_SUEL_P1),sueloItem.create(250,IMG_SUEL_P1),sueloItem.create(251,IMG_SUEL_P1),sueloItem.create(252,IMG_SUEL_P1),sueloItem.create(253,IMG_SUEL_P1),sueloItem.create(254,IMG_SUEL_P1),sueloItem.create(255,IMG_SUEL_P1),sueloItem.create(256,IMG_SUEL_P1),sueloItem.create(257,IMG_SUEL_P1),sueloItem.create(258,IMG_SUEL_P1),sueloItem.create(259,IMG_SUEL_P1),sueloItem.create(260,IMG_SUEL_P1),sueloItem.create(261,IMG_SUEL_P1),sueloItem.create(262,IMG_SUEL_P1),sueloItem.create(263,IMG_SUEL_P1),sueloItem.create(264,IMG_SUEL_P1),sueloItem.create(265,IMG_SUEL_P1),sueloItem.create(266,IMG_SUEL_P1),sueloItem.create(267,IMG_SUEL_P1),sueloItem.create(268,IMG_SUEL_P1),sueloItem.create(269,IMG_SUEL_P1),sueloItem.create(270,IMG_SUEL_P1),sueloItem.create(271,IMG_SUEL_P1),sueloItem.create(272,IMG_SUEL_P1),sueloItem.create(273,IMG_SUEL_P1),sueloItem.create(274,IMG_SUEL_P1),sueloItem.create(275,IMG_SUEL_P1),sueloItem.create(276,IMG_SUEL_P1),sueloItem.create(277,IMG_SUEL_P1),sueloItem.create(278,IMG_SUEL_P1),sueloItem.create(279,IMG_SUEL_P1),sueloItem.create(280,IMG_SUEL_P1),sueloItem.create(281,IMG_SUEL_P1),sueloItem.create(282,IMG_SUEL_P1),sueloItem.create(283,IMG_SUEL_P1),paredItem.create(284,IMG_MURO_H1),
            paredItem.create(285,IMG_MURO_H1),sueloItem.create(286,IMG_SUEL_P1),sueloItem.create(287,IMG_SUEL_P1),sueloItem.create(288,IMG_SUEL_P1),sueloItem.create(289,IMG_SUEL_P1),sueloItem.create(290,IMG_SUEL_P1),sueloItem.create(291,IMG_SUEL_P1),sueloItem.create(292,IMG_SUEL_P1),sueloItem.create(293,IMG_SUEL_P1),sueloItem.create(294,IMG_SUEL_P1),sueloItem.create(295,IMG_SUEL_P1),sueloItem.create(296,IMG_SUEL_P1),sueloItem.create(297,IMG_SUEL_P1),sueloItem.create(298,IMG_SUEL_P1),sueloItem.create(299,IMG_SUEL_P1),sueloItem.create(300,IMG_SUEL_P1),sueloItem.create(301,IMG_SUEL_P1),sueloItem.create(302,IMG_SUEL_P1),sueloItem.create(303,IMG_SUEL_P1),sueloItem.create(304,IMG_SUEL_P1),sueloItem.create(305,IMG_SUEL_P1),sueloItem.create(306,IMG_SUEL_P1),sueloItem.create(307,IMG_SUEL_P1),sueloItem.create(308,IMG_SUEL_P1),sueloItem.create(309,IMG_SUEL_P1),sueloItem.create(310,IMG_SUEL_P1),sueloItem.create(311,IMG_SUEL_P1),sueloItem.create(312,IMG_SUEL_P1),sueloItem.create(313,IMG_SUEL_P1),sueloItem.create(314,IMG_SUEL_P1),sueloItem.create(315,IMG_SUEL_P1),sueloItem.create(316,IMG_SUEL_P1),sueloItem.create(317,IMG_SUEL_P1),sueloItem.create(318,IMG_SUEL_P1),sueloItem.create(319,IMG_SUEL_P1),sueloItem.create(320,IMG_SUEL_P1),paredItem.create(321,IMG_MURO_H1),
            paredItem.create(322,IMG_MURO_H1),sueloItem.create(323,IMG_SUEL_P1),sueloItem.create(324,IMG_SUEL_P1),sueloItem.create(325,IMG_SUEL_P1),sueloItem.create(326,IMG_SUEL_P1),sueloItem.create(327,IMG_SUEL_P1),sueloItem.create(328,IMG_SUEL_P1),sueloItem.create(329,IMG_SUEL_P1),sueloItem.create(330,IMG_SUEL_P1),sueloItem.create(331,IMG_SUEL_P1),sueloItem.create(332,IMG_SUEL_P1),sueloItem.create(333,IMG_SUEL_P1),sueloItem.create(334,IMG_SUEL_P1),sueloItem.create(335,IMG_SUEL_P1),sueloItem.create(336,IMG_SUEL_P1),sueloItem.create(337,IMG_SUEL_P1),sueloItem.create(338,IMG_SUEL_P1),sueloItem.create(339,IMG_SUEL_P1),sueloItem.create(340,IMG_SUEL_P1),sueloItem.create(341,IMG_SUEL_P1),sueloItem.create(342,IMG_SUEL_P1),sueloItem.create(343,IMG_SUEL_P1),sueloItem.create(344,IMG_SUEL_P1),sueloItem.create(345,IMG_SUEL_P1),sueloItem.create(346,IMG_SUEL_P1),sueloItem.create(347,IMG_SUEL_P1),sueloItem.create(348,IMG_SUEL_P1),sueloItem.create(349,IMG_SUEL_P1),sueloItem.create(350,IMG_SUEL_P1),sueloItem.create(351,IMG_SUEL_P1),sueloItem.create(352,IMG_SUEL_P1),sueloItem.create(353,IMG_SUEL_P1),sueloItem.create(354,IMG_SUEL_P1),sueloItem.create(355,IMG_SUEL_P1),sueloItem.create(356,IMG_SUEL_P1),sueloItem.create(357,IMG_SUEL_P1),paredItem.create(358,IMG_MURO_H1),
            paredItem.create(359,IMG_MURO_H1),sueloItem.create(360,IMG_SUEL_P1),sueloItem.create(361,IMG_SUEL_P1),sueloItem.create(362,IMG_SUEL_P1),sueloItem.create(363,IMG_SUEL_P1),sueloItem.create(364,IMG_SUEL_P1),sueloItem.create(365,IMG_SUEL_P1),sueloItem.create(366,IMG_SUEL_P1),sueloItem.create(367,IMG_SUEL_P1),sueloItem.create(368,IMG_SUEL_P1),sueloItem.create(369,IMG_SUEL_P1),sueloItem.create(370,IMG_SUEL_P1),sueloItem.create(371,IMG_SUEL_P1),sueloItem.create(372,IMG_SUEL_P1),sueloItem.create(373,IMG_SUEL_P1),sueloItem.create(374,IMG_SUEL_P1),sueloItem.create(375,IMG_SUEL_P1),sueloItem.create(376,IMG_SUEL_P1),sueloItem.create(377,IMG_SUEL_P1),sueloItem.create(378,IMG_SUEL_P1),sueloItem.create(379,IMG_SUEL_P1),sueloItem.create(380,IMG_SUEL_P1),sueloItem.create(381,IMG_SUEL_P1),sueloItem.create(382,IMG_SUEL_P1),sueloItem.create(383,IMG_SUEL_P1),sueloItem.create(384,IMG_SUEL_P1),sueloItem.create(385,IMG_SUEL_P1),sueloItem.create(386,IMG_SUEL_P1),sueloItem.create(387,IMG_SUEL_P1),sueloItem.create(388,IMG_SUEL_P1),sueloItem.create(389,IMG_SUEL_P1),sueloItem.create(390,IMG_SUEL_P1),sueloItem.create(391,IMG_SUEL_P1),sueloItem.create(392,IMG_SUEL_P1),sueloItem.create(393,IMG_SUEL_P1),sueloItem.create(394,IMG_SUEL_P1),paredItem.create(395,IMG_MURO_H1),
            paredItem.create(396,IMG_MURO_H1),sueloItem.create(397,IMG_SUEL_P1),sueloItem.create(398,IMG_SUEL_P1),sueloItem.create(399,IMG_SUEL_P1),sueloItem.create(400,IMG_SUEL_P1),sueloItem.create(401,IMG_SUEL_P1),sueloItem.create(402,IMG_SUEL_P1),sueloItem.create(403,IMG_SUEL_P1),sueloItem.create(404,IMG_SUEL_P1),sueloItem.create(405,IMG_SUEL_P1),sueloItem.create(406,IMG_SUEL_P1),sueloItem.create(407,IMG_SUEL_P1),sueloItem.create(408,IMG_SUEL_P1),sueloItem.create(409,IMG_SUEL_P1),sueloItem.create(410,IMG_SUEL_P1),sueloItem.create(411,IMG_SUEL_P1),sueloItem.create(412,IMG_SUEL_P1),sueloItem.create(413,IMG_SUEL_P1),sueloItem.create(414,IMG_SUEL_P1),sueloItem.create(415,IMG_SUEL_P1),sueloItem.create(416,IMG_SUEL_P1),sueloItem.create(417,IMG_SUEL_P1),sueloItem.create(418,IMG_SUEL_P1),sueloItem.create(419,IMG_SUEL_P1),sueloItem.create(420,IMG_SUEL_P1),sueloItem.create(421,IMG_SUEL_P1),sueloItem.create(422,IMG_SUEL_P1),sueloItem.create(423,IMG_SUEL_P1),sueloItem.create(424,IMG_SUEL_P1),sueloItem.create(425,IMG_SUEL_P1),sueloItem.create(426,IMG_SUEL_P1),sueloItem.create(427,IMG_SUEL_P1),sueloItem.create(428,IMG_SUEL_P1),sueloItem.create(429,IMG_SUEL_P1),sueloItem.create(430,IMG_SUEL_P1),sueloItem.create(431,IMG_SUEL_P1),paredItem.create(432,IMG_MURO_H1),
            paredItem.create(433,IMG_MURO_H1),sueloItem.create(434,IMG_SUEL_P1),sueloItem.create(435,IMG_SUEL_P1),sueloItem.create(436,IMG_SUEL_P1),sueloItem.create(437,IMG_SUEL_P1),sueloItem.create(438,IMG_SUEL_P1),sueloItem.create(439,IMG_SUEL_P1),sueloItem.create(440,IMG_SUEL_P1),sueloItem.create(441,IMG_SUEL_P1),sueloItem.create(442,IMG_SUEL_P1),sueloItem.create(443,IMG_SUEL_P1),sueloItem.create(444,IMG_SUEL_P1),sueloItem.create(445,IMG_SUEL_P1),sueloItem.create(446,IMG_SUEL_P1),sueloItem.create(447,IMG_SUEL_P1),sueloItem.create(448,IMG_SUEL_P1),sueloItem.create(449,IMG_SUEL_P1),sueloItem.create(450,IMG_SUEL_P1),sueloItem.create(451,IMG_SUEL_P1),sueloItem.create(452,IMG_SUEL_P1),sueloItem.create(453,IMG_SUEL_P1),sueloItem.create(454,IMG_SUEL_P1),sueloItem.create(455,IMG_SUEL_P1),sueloItem.create(456,IMG_SUEL_P1),sueloItem.create(457,IMG_SUEL_P1),sueloItem.create(458,IMG_SUEL_P1),sueloItem.create(459,IMG_SUEL_P1),sueloItem.create(460,IMG_SUEL_P1),sueloItem.create(461,IMG_SUEL_P1),sueloItem.create(462,IMG_SUEL_P1),sueloItem.create(463,IMG_SUEL_P1),sueloItem.create(464,IMG_SUEL_P1),sueloItem.create(465,IMG_SUEL_P1),sueloItem.create(466,IMG_SUEL_P1),sueloItem.create(467,IMG_SUEL_P1),sueloItem.create(468,IMG_SUEL_P1),paredItem.create(469,IMG_MURO_H1),
            paredItem.create(370,IMG_MURO_H1),sueloItem.create(371,IMG_SUEL_P1),sueloItem.create(372,IMG_SUEL_P1),sueloItem.create(473,IMG_SUEL_P1),sueloItem.create(474,IMG_SUEL_P1),sueloItem.create(475,IMG_SUEL_P1),sueloItem.create(476,IMG_SUEL_P1),sueloItem.create(477,IMG_SUEL_P1),sueloItem.create(478,IMG_SUEL_P1),sueloItem.create(479,IMG_SUEL_P1),sueloItem.create(480,IMG_SUEL_P1),sueloItem.create(481,IMG_SUEL_P1),sueloItem.create(482,IMG_SUEL_P1),sueloItem.create(483,IMG_SUEL_P1),sueloItem.create(484,IMG_SUEL_P1),sueloItem.create(485,IMG_SUEL_P1),sueloItem.create(486,IMG_SUEL_P1),sueloItem.create(487,IMG_SUEL_P1),sueloItem.create(488,IMG_SUEL_P1),sueloItem.create(489,IMG_SUEL_P1),sueloItem.create(490,IMG_SUEL_P1),sueloItem.create(491,IMG_SUEL_P1),sueloItem.create(492,IMG_SUEL_P1),sueloItem.create(493,IMG_SUEL_P1),sueloItem.create(494,IMG_SUEL_P1),sueloItem.create(495,IMG_SUEL_P1),sueloItem.create(496,IMG_SUEL_P1),sueloItem.create(497,IMG_SUEL_P1),sueloItem.create(498,IMG_SUEL_P1),sueloItem.create(499,IMG_SUEL_P1),sueloItem.create(500,IMG_SUEL_P1),sueloItem.create(501,IMG_SUEL_P1),sueloItem.create(502,IMG_SUEL_P1),sueloItem.create(503,IMG_SUEL_P1),sueloItem.create(504,IMG_SUEL_P1),sueloItem.create(505,IMG_SUEL_P1),paredItem.create(506,IMG_MURO_H1),
            paredItem.create(507,IMG_MURO_H1),sueloItem.create(508,IMG_SUEL_P1),sueloItem.create(509,IMG_SUEL_P1),sueloItem.create(510,IMG_SUEL_P1),sueloItem.create(511,IMG_SUEL_P1),sueloItem.create(512,IMG_SUEL_P1),sueloItem.create(513,IMG_SUEL_P1),sueloItem.create(514,IMG_SUEL_P1),sueloItem.create(515,IMG_SUEL_P1),sueloItem.create(516,IMG_SUEL_P1),sueloItem.create(517,IMG_SUEL_P1),sueloItem.create(518,IMG_SUEL_P1),sueloItem.create(519,IMG_SUEL_P1),sueloItem.create(520,IMG_SUEL_P1),sueloItem.create(521,IMG_SUEL_P1),sueloItem.create(522,IMG_SUEL_P1),sueloItem.create(523,IMG_SUEL_P1),sueloItem.create(524,IMG_SUEL_P1),sueloItem.create(525,IMG_SUEL_P1),sueloItem.create(526,IMG_SUEL_P1),sueloItem.create(527,IMG_SUEL_P1),sueloItem.create(528,IMG_SUEL_P1),sueloItem.create(529,IMG_SUEL_P1),sueloItem.create(530,IMG_SUEL_P1),sueloItem.create(531,IMG_SUEL_P1),sueloItem.create(532,IMG_SUEL_P1),sueloItem.create(533,IMG_SUEL_P1),sueloItem.create(534,IMG_SUEL_P1),sueloItem.create(535,IMG_SUEL_P1),sueloItem.create(536,IMG_SUEL_P1),sueloItem.create(537,IMG_SUEL_P1),sueloItem.create(538,IMG_SUEL_P1),sueloItem.create(539,IMG_SUEL_P1),sueloItem.create(540,IMG_SUEL_P1),sueloItem.create(541,IMG_SUEL_P1),sueloItem.create(542,IMG_SUEL_P1),paredItem.create(543,IMG_MURO_H1),
            paredItem.create(544,IMG_MURO_H1),sueloItem.create(545,IMG_SUEL_P1),sueloItem.create(546,IMG_SUEL_P1),sueloItem.create(547,IMG_SUEL_P1),sueloItem.create(548,IMG_SUEL_P1),sueloItem.create(549,IMG_SUEL_P1),sueloItem.create(550,IMG_SUEL_P1),sueloItem.create(551,IMG_SUEL_P1),sueloItem.create(552,IMG_SUEL_P1),sueloItem.create(553,IMG_SUEL_P1),sueloItem.create(554,IMG_SUEL_P1),sueloItem.create(555,IMG_SUEL_P1),sueloItem.create(556,IMG_SUEL_P1),sueloItem.create(557,IMG_SUEL_P1),sueloItem.create(558,IMG_SUEL_P1),sueloItem.create(559,IMG_SUEL_P1),sueloItem.create(560,IMG_SUEL_P1),sueloItem.create(561,IMG_SUEL_P1),sueloItem.create(562,IMG_SUEL_P1),sueloItem.create(563,IMG_SUEL_P1),sueloItem.create(564,IMG_SUEL_P1),sueloItem.create(565,IMG_SUEL_P1),sueloItem.create(566,IMG_SUEL_P1),sueloItem.create(567,IMG_SUEL_P1),sueloItem.create(568,IMG_SUEL_P1),sueloItem.create(569,IMG_SUEL_P1),sueloItem.create(570,IMG_SUEL_P1),sueloItem.create(571,IMG_SUEL_P1),sueloItem.create(572,IMG_SUEL_P1),sueloItem.create(573,IMG_SUEL_P1),sueloItem.create(574,IMG_SUEL_P1),sueloItem.create(575,IMG_SUEL_P1),sueloItem.create(576,IMG_SUEL_P1),sueloItem.create(577,IMG_SUEL_P1),sueloItem.create(578,IMG_SUEL_P1),sueloItem.create(579,IMG_SUEL_P1),paredItem.create(580,IMG_MURO_H1),
            paredItem.create(581,IMG_MURO_H1),sueloItem.create(582,IMG_SUEL_P1),sueloItem.create(583,IMG_SUEL_P1),sueloItem.create(584,IMG_SUEL_P1),sueloItem.create(585,IMG_SUEL_P1),sueloItem.create(586,IMG_SUEL_P1),sueloItem.create(587,IMG_SUEL_P1),sueloItem.create(588,IMG_SUEL_P1),sueloItem.create(589,IMG_SUEL_P1),sueloItem.create(590,IMG_SUEL_P1),sueloItem.create(591,IMG_SUEL_P1),sueloItem.create(592,IMG_SUEL_P1),sueloItem.create(593,IMG_SUEL_P1),sueloItem.create(594,IMG_SUEL_P1),sueloItem.create(595,IMG_SUEL_P1),sueloItem.create(596,IMG_SUEL_P1),sueloItem.create(597,IMG_SUEL_P1),sueloItem.create(598,IMG_SUEL_P1),sueloItem.create(599,IMG_SUEL_P1),sueloItem.create(600,IMG_SUEL_P1),sueloItem.create(601,IMG_SUEL_P1),sueloItem.create(602,IMG_SUEL_P1),sueloItem.create(603,IMG_SUEL_P1),sueloItem.create(604,IMG_SUEL_P1),sueloItem.create(605,IMG_SUEL_P1),sueloItem.create(606,IMG_SUEL_P1),sueloItem.create(607,IMG_SUEL_P1),sueloItem.create(608,IMG_SUEL_P1),sueloItem.create(609,IMG_SUEL_P1),sueloItem.create(610,IMG_SUEL_P1),sueloItem.create(611,IMG_SUEL_P1),sueloItem.create(612,IMG_SUEL_P1),sueloItem.create(613,IMG_SUEL_P1),sueloItem.create(614,IMG_SUEL_P1),sueloItem.create(615,IMG_SUEL_P1),sueloItem.create(616,IMG_SUEL_P1),paredItem.create(617,IMG_MURO_H1),
            paredItem.create(618,IMG_MURO_H1),sueloItem.create(619,IMG_SUEL_P1),sueloItem.create(620,IMG_SUEL_P1),sueloItem.create(621,IMG_SUEL_P1),sueloItem.create(622,IMG_SUEL_P1),sueloItem.create(623,IMG_SUEL_P1),sueloItem.create(624,IMG_SUEL_P1),sueloItem.create(625,IMG_SUEL_P1),sueloItem.create(626,IMG_SUEL_P1),sueloItem.create(627,IMG_SUEL_P1),sueloItem.create(628,IMG_SUEL_P1),sueloItem.create(629,IMG_SUEL_P1),sueloItem.create(630,IMG_SUEL_P1),sueloItem.create(631,IMG_SUEL_P1),sueloItem.create(632,IMG_SUEL_P1),sueloItem.create(633,IMG_SUEL_P1),sueloItem.create(634,IMG_SUEL_P1),sueloItem.create(635,IMG_SUEL_P1),sueloItem.create(636,IMG_SUEL_P1),sueloItem.create(637,IMG_SUEL_P1),sueloItem.create(638,IMG_SUEL_P1),sueloItem.create(639,IMG_SUEL_P1),sueloItem.create(640,IMG_SUEL_P1),sueloItem.create(641,IMG_SUEL_P1),sueloItem.create(642,IMG_SUEL_P1),sueloItem.create(643,IMG_SUEL_P1),sueloItem.create(644,IMG_SUEL_P1),sueloItem.create(645,IMG_SUEL_P1),sueloItem.create(646,IMG_SUEL_P1),sueloItem.create(647,IMG_SUEL_P1),sueloItem.create(648,IMG_SUEL_P1),sueloItem.create(649,IMG_SUEL_P1),sueloItem.create(650,IMG_SUEL_P1),sueloItem.create(651,IMG_SUEL_P1),sueloItem.create(652,IMG_SUEL_P1),sueloItem.create(653,IMG_SUEL_P1),paredItem.create(654,IMG_MURO_H1),
            paredItem.create(655,IMG_MURO_H1),sueloItem.create(656,IMG_SUEL_P1),sueloItem.create(657,IMG_SUEL_P1),sueloItem.create(658,IMG_SUEL_P1),sueloItem.create(659,IMG_SUEL_P1),sueloItem.create(660,IMG_SUEL_P1),sueloItem.create(661,IMG_SUEL_P1),sueloItem.create(662,IMG_SUEL_P1),sueloItem.create(663,IMG_SUEL_P1),sueloItem.create(664,IMG_SUEL_P1),sueloItem.create(665,IMG_SUEL_P1),sueloItem.create(666,IMG_SUEL_P1),sueloItem.create(667,IMG_SUEL_P1),sueloItem.create(668,IMG_SUEL_P1),sueloItem.create(669,IMG_SUEL_P1),sueloItem.create(670,IMG_SUEL_P1),sueloItem.create(671,IMG_SUEL_P1),sueloItem.create(672,IMG_SUEL_P1),sueloItem.create(673,IMG_SUEL_P1),sueloItem.create(674,IMG_SUEL_P1),sueloItem.create(675,IMG_SUEL_P1),sueloItem.create(676,IMG_SUEL_P1),sueloItem.create(677,IMG_SUEL_P1),sueloItem.create(678,IMG_SUEL_P1),sueloItem.create(679,IMG_SUEL_P1),sueloItem.create(680,IMG_SUEL_P1),sueloItem.create(681,IMG_SUEL_P1),sueloItem.create(682,IMG_SUEL_P1),sueloItem.create(683,IMG_SUEL_P1),sueloItem.create(684,IMG_SUEL_P1),sueloItem.create(685,IMG_SUEL_P1),sueloItem.create(686,IMG_SUEL_P1),sueloItem.create(687,IMG_SUEL_P1),sueloItem.create(688,IMG_SUEL_P1),sueloItem.create(689,IMG_SUEL_P1),sueloItem.create(690,IMG_SUEL_P1),paredItem.create(691,IMG_MURO_H1),
            paredItem.create(692,IMG_MURO_H1),sueloItem.create(693,IMG_SUEL_P1),sueloItem.create(694,IMG_SUEL_P1),sueloItem.create(695,IMG_SUEL_P1),sueloItem.create(696,IMG_SUEL_P1),sueloItem.create(697,IMG_SUEL_P1),sueloItem.create(698,IMG_SUEL_P1),sueloItem.create(699,IMG_SUEL_P1),sueloItem.create(700,IMG_SUEL_P1),sueloItem.create(701,IMG_SUEL_P1),sueloItem.create(702,IMG_SUEL_P1),sueloItem.create(703,IMG_SUEL_P1),sueloItem.create(704,IMG_SUEL_P1),sueloItem.create(705,IMG_SUEL_P1),sueloItem.create(706,IMG_SUEL_P1),sueloItem.create(707,IMG_SUEL_P1),sueloItem.create(708,IMG_SUEL_P1),sueloItem.create(709,IMG_SUEL_P1),sueloItem.create(710,IMG_SUEL_P1),sueloItem.create(711,IMG_SUEL_P1),sueloItem.create(712,IMG_SUEL_P1),sueloItem.create(713,IMG_SUEL_P1),sueloItem.create(714,IMG_SUEL_P1),sueloItem.create(715,IMG_SUEL_P1),sueloItem.create(716,IMG_SUEL_P1),sueloItem.create(717,IMG_SUEL_P1),sueloItem.create(718,IMG_SUEL_P1),sueloItem.create(719,IMG_SUEL_P1),sueloItem.create(720,IMG_SUEL_P1),sueloItem.create(721,IMG_SUEL_P1),sueloItem.create(722,IMG_SUEL_P1),sueloItem.create(723,IMG_SUEL_P1),sueloItem.create(724,IMG_SUEL_P1),sueloItem.create(725,IMG_SUEL_P1),sueloItem.create(726,IMG_SUEL_P1),sueloItem.create(727,IMG_SUEL_P1),paredItem.create(728,IMG_MURO_H1),
            paredItem.create(729,IMG_MURO_H1),sueloItem.create(730,IMG_SUEL_P1),sueloItem.create(731,IMG_SUEL_P1),sueloItem.create(732,IMG_SUEL_P1),sueloItem.create(733,IMG_SUEL_P1),sueloItem.create(734,IMG_SUEL_P1),sueloItem.create(735,IMG_SUEL_P1),sueloItem.create(736,IMG_SUEL_P1),sueloItem.create(737,IMG_SUEL_P1),sueloItem.create(738,IMG_SUEL_P1),sueloItem.create(739,IMG_SUEL_P1),sueloItem.create(740,IMG_SUEL_P1),sueloItem.create(741,IMG_SUEL_P1),sueloItem.create(742,IMG_SUEL_P1),sueloItem.create(743,IMG_SUEL_P1),sueloItem.create(744,IMG_SUEL_P1),sueloItem.create(745,IMG_SUEL_P1),sueloItem.create(746,IMG_SUEL_P1),sueloItem.create(747,IMG_SUEL_P1),sueloItem.create(748,IMG_SUEL_P1),sueloItem.create(749,IMG_SUEL_P1),sueloItem.create(750,IMG_SUEL_P1),sueloItem.create(751,IMG_SUEL_P1),sueloItem.create(752,IMG_SUEL_P1),sueloItem.create(753,IMG_SUEL_P1),sueloItem.create(754,IMG_SUEL_P1),sueloItem.create(755,IMG_SUEL_P1),sueloItem.create(756,IMG_SUEL_P1),sueloItem.create(757,IMG_SUEL_P1),sueloItem.create(758,IMG_SUEL_P1),sueloItem.create(759,IMG_SUEL_P1),sueloItem.create(760,IMG_SUEL_P1),sueloItem.create(761,IMG_SUEL_P1),sueloItem.create(762,IMG_SUEL_P1),sueloItem.create(763,IMG_SUEL_P1),sueloItem.create(764,IMG_SUEL_P1),paredItem.create(765,IMG_MURO_H1),
            paredItem.create(766,IMG_MURO_H1),paredItem.create(767,IMG_MURO_H1),paredItem.create(768,IMG_MURO_H1),paredItem.create(769,IMG_MURO_H1),paredItem.create(770,IMG_MURO_H1),paredItem.create(771,IMG_MURO_H1),paredItem.create(772,IMG_MURO_H1),paredItem.create(773,IMG_MURO_H1),paredItem.create(774,IMG_MURO_H1),paredItem.create(775,IMG_MURO_H1),paredItem.create(776,IMG_MURO_H1),paredItem.create(777,IMG_MURO_H1),paredItem.create(778,IMG_MURO_H1),paredItem.create(779,IMG_MURO_H1),paredItem.create(780,IMG_MURO_H1),paredItem.create(781,IMG_MURO_H1),paredItem.create(782,IMG_MURO_H1),paredItem.create(783,IMG_MURO_H1),paredItem.create(784,IMG_MURO_H1),paredItem.create(785,IMG_MURO_H1),paredItem.create(786,IMG_MURO_H1),paredItem.create(787,IMG_MURO_H1),paredItem.create(788,IMG_MURO_H1),paredItem.create(789,IMG_MURO_H1),paredItem.create(790,IMG_MURO_H1),paredItem.create(791,IMG_MURO_H1),paredItem.create(792,IMG_MURO_H1),paredItem.create(793,IMG_MURO_H1),paredItem.create(794,IMG_MURO_H1),paredItem.create(795,IMG_MURO_H1),paredItem.create(796,IMG_MURO_H1),paredItem.create(797,IMG_MURO_H1),paredItem.create(798,IMG_MURO_H1),paredItem.create(799,IMG_MURO_H1),paredItem.create(800,IMG_MURO_H1),paredItem.create(801,IMG_MURO_H1),paredItem.create(802,IMG_MURO_H1)
    };


    private ArrayList<Dungeon> colisiones = new ArrayList<>();

    public Game(String type, String name, String level) {

        frame.setContentPane(scenarioPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setPreferredSize(new Dimension(1320, 645));
        frame.setSize(frame.getPreferredSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);

        //keyListenerAvanzado();


        scorePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        scorePanel.setPreferredSize(new Dimension(120,frame.getHeight()));
        scorePanel.setSize(scorePanel.getPreferredSize());
        gamePanel.setLayout(null);
        gamePanel.setPreferredSize(new Dimension(1184,645));
        gamePanel.setSize(gamePanel.getPreferredSize());

        itemsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        itemsPanel.setPreferredSize(new Dimension(120,137));
        itemsPanel.setSize(itemsPanel.getPreferredSize());

        scenarioPanel.setLayout(new BorderLayout());

        anchura = gamePanel.getWidth() ;
        playerType = type;

        fillScenario();
        addDungeonItems();
        createItems(type,name, level);

        lblPlayerInfo.setFont(new Font("Calibri", Font.BOLD, 20));
        lblPlayerInfo.setForeground(Color.WHITE);
        lblPlayerInfo.setPreferredSize(new Dimension(scorePanel.getWidth(),20));
        lblPlayerInfo.setSize(lblPlayerInfo.getPreferredSize());

        iconName.setPreferredSize(new Dimension(39,74));
        iconName.setSize(iconName.getPreferredSize());
        setIcon(IMG_CARA_PARADO,iconName);
        iconName.setIcon(icon);

        lblName.setFont(new Font("Calibri", Font.BOLD, 12));
        lblName.setForeground(Color.WHITE);
        lblName.setText(player.getName());
        lblName.setToolTipText(lblName.getText());
        lblName.setPreferredSize(new Dimension(scorePanel.getWidth(),30));
        lblName.setSize(lblName.getPreferredSize());

        iconLifes.setPreferredSize(new Dimension(24,24));
        iconLifes.setSize(iconLifes.getPreferredSize());
        setIcon(IMG_COR,iconLifes);
        iconLifes.setIcon(icon);

        lblLifes.setFont(new Font("Calibri", Font.BOLD, 20));
        lblLifes.setForeground(Color.WHITE);
        lblLifes.setText(String.valueOf(player.getLifes()));
        lblLifes.setPreferredSize(new Dimension(80,30));
        lblLifes.setSize(lblLifes.getPreferredSize());


        iconGold.setPreferredSize(new Dimension(24,24));
        iconGold.setSize(iconGold.getPreferredSize());
        setIcon(IMG_GOLD,iconGold);
        iconGold.setIcon(icon);

        lblGoldInfo.setFont(new Font("Calibri", Font.BOLD, 20));
        lblGoldInfo.setForeground(Color.WHITE);
        lblGoldInfo.setPreferredSize(new Dimension(80,30));
        lblGoldInfo.setSize(lblGoldInfo.getPreferredSize());

        lblItemsList.setFont(new Font("Calibri", Font.BOLD, 20));
        lblItemsList.setForeground(Color.WHITE);
        lblItemsList.setPreferredSize(new Dimension(200,20));
        lblItemsList.setSize(lblItemsList.getPreferredSize());

        lblPoisoned.setFont(new Font("Calibri", Font.BOLD, 16));
        lblPoisoned.setForeground(Color.decode(red));
        lblPoisoned.setPreferredSize(new Dimension(100,20));
        lblPoisoned.setSize(lblPoisoned.getPreferredSize());

        timeChecker = new Timer(1000, new TimerActionListener());
        timeChecker.start();

        lblTime.setFont(new Font("Calibri", Font.BOLD, 16));
        lblTime.setForeground(Color.WHITE);
        lblTime.setPreferredSize(new Dimension(100,20));
        lblTime.setSize(lblTime.getPreferredSize());

        lblTimeCounter.setFont(new Font("Calibri", Font.BOLD, 19));
        lblTimeCounter.setForeground(Color.RED);
        lblTimeCounter.setPreferredSize(new Dimension(100,60));
        lblTimeCounter.setSize(lblTimeCounter.getPreferredSize());


        scorePanel.add(lblTime);
        scorePanel.add(lblTimeCounter);
        scorePanel.add(lblPlayerInfo);
        scorePanel.add(lblPoisoned);
        scorePanel.add(iconName);
        scorePanel.add(lblName);
        scorePanel.add(iconLifes);
        scorePanel.add(lblLifes);
        scorePanel.add(iconGold);
        scorePanel.add(lblGoldInfo);
        itemsPanel.add(lblItemsList);
        scorePanel.add(itemsPanel);
        scorePanel.add(controlsPanel);


        controlsPanel.setLayout(null);
        controlsPanel.setPreferredSize(new Dimension(120,frame.getHeight()));
        controlsPanel.setSize(controlsPanel.getPreferredSize());


        lblControls.setPreferredSize(new Dimension(191,171));
        lblControls.setSize(lblControls.getPreferredSize());
        final String IMG_CONTROLS = "img/dungeon/controls.png";
        urlPlayer = getClass().getResource(IMG_CONTROLS);
        iconPlayer = new ImageIcon(urlPlayer);
        icon = new ImageIcon(iconPlayer.getImage().getScaledInstance(120,102,Image.SCALE_DEFAULT));
        lblControls.setIcon(icon);

        controlsPanel.add(lblControls);



        scenarioPanel.add(scorePanel,BorderLayout.WEST);
        scenarioPanel.add(gamePanel, BorderLayout.CENTER);

        gamePanel.setBackground(Color.BLACK);

        scorePanel.setBackground(Color.decode("#454da3"));
        itemsPanel.setBackground(Color.decode("#454da3"));
        controlsPanel.setBackground(Color.decode("#454da3"));

        keyEvents();
        lblPlayer.setFocusable(true);
        lblPlayer.requestFocus();
        horaInicio = new Date();
    }

    private void addDungeonItems() {

        int rndItem = -1;
        int index = 0;
        ArrayList<Integer> noValid = new ArrayList<>();
        setNoValids(noValid);
        boolean valid = false;
        boolean salir = false;

        //long start2 = System.currentTimeMillis();

        for(int i=0;i < 5;i++){

            while(!valid){
                rndItem = new Random().nextInt(items.length);
                while(index < noValid.size() && !salir){
                    if(noValid.contains(rndItem)){
                        valid = false;
                        salir = true;
                        index = 0;
                    }else{
                        valid = true;
                    }
                    index++;
                }
                salir = false;
            }

            noValid.add(rndItem);

            switch (i){
                case 0:
                    potionItem = new Potion("potion",null, null,null,"", IMG_POCIO, DUNGEON_ITEM_WIDTH, DUNGEON_ITEM_HEIGHT,0,0,0,0);
                    itemPotion = potionItem.create(rndItem,IMG_POCIO);
                    addItem(itemPotion,items[rndItem].getX(),items[rndItem].getY());
                    potionItem.setLabel(itemPotion);
                    colisiones.add(potionItem);
                    break;
                case 1:
                    mitraItem = new Mitra("mitra",null, null,null,"", IMG_MITRA, DUNGEON_ITEM_WIDTH, DUNGEON_ITEM_HEIGHT,0,0,0,0);
                    itemMitra = mitraItem.create(rndItem,IMG_MITRA);
                    addItem(itemMitra,items[rndItem].getX(),items[rndItem].getY());
                    mitraItem.setLabel(itemMitra);
                    colisiones.add(mitraItem);
                    break;
                case 2:
                    swordItem = new Sword("sword",null, null,null,"", IMG_ESPASA, DUNGEON_ITEM_WIDTH, DUNGEON_ITEM_HEIGHT,0,0,0,0);
                    itemSword = swordItem.create(rndItem,IMG_ESPASA);
                    addItem(itemSword,items[rndItem].getX(),items[rndItem].getY());
                    swordItem.setLabel(itemSword);
                    colisiones.add(swordItem);
                    break;
                case 3:
                    goldItem = new Gold("gold",null, null, null,"", IMG_GOLD, DUNGEON_ITEM_WIDTH, DUNGEON_ITEM_HEIGHT,0,0,0,0);
                    itemCoins = goldItem.create(rndItem,IMG_GOLD);
                    addItem(itemCoins,items[rndItem].getX(),items[rndItem].getY());
                    goldItem.setLabel(itemCoins);
                    colisiones.add(goldItem);
                    break;
            }
            valid = false;
            index = 0;
        }

        //long end2 = System.currentTimeMillis();
        //System.out.println("Elapsed Time in milli seconds: "+ (end2-start2));
    }

    private void setNoValids(ArrayList<Integer> noValid) {

        for(int i=0;i < items.length;i++){
            if(!items[i].getToolTipText().equals("terra")){
                noValid.add(i);
            }
        }
    }

    private void addItem(JLabel item, int x, int y) {

        item.setLocation(x,y);
        gamePanel.add(item,0);
    }

    private void fillScenario() {

        int a = 0;
        int b = 0;

        for(JLabel item : items){
            if (a >= (anchura)) {
                b = b + 32;
                a = 0;
            }

            item.setLocation(a, b);
            if(item.getToolTipText().equals("mur") || item.getToolTipText().equals("exit")){
                paredItem = new Mur("mur",null, null, null,"", IMG_MURO_H1, ANCHO_LABELS, ALTO_LABELS,item.getX(),item.getY(),0,0);
                paredItem.setLabel(item);
                paredItem.setValueX(item.getX());
                paredItem.setValueY(item.getY());
                colisiones.add(paredItem);
            }
            gamePanel.add(item,0);
            a += 32;
        }
    }

    private void updatePlayerPosition(JLabel personaje, int x, int y) {

        player.setCoordX(x);
        player.setCoordY(y);
        personaje.setLocation(player.getCoordX(), player.getCoordY());
        healthPlayer.setLocation(x, y - 10);
    }

    private void createItems(String type,String name, String level) {

        int rnd = 0;
        int skullSpeed = 5;

        setPlayer(type, name);

        Skull skull = null;
        JLabel lblSkull;
        JProgressBar healthSkull;
        Timer skullTimer;


        for(int i = 0;i < 11;i++){
            switch (i){
                case 0:
                    lblSkull = new JLabel();
                    healthSkull = new JProgressBar(0,500);
                    rnd = new Random().nextInt(100 - 60 + 1) + 60;
                    skull = new Skull("skull", healthSkull, lblSkull,null,"baja",IMG_SKULL_DOWN,ANCHO_CHARACTERS,ALTO_CHARACTERS,rnd,200,skullSpeed,500);
                    setSkull(skull, lblSkull, skullsList);
                    skullTimer = new Timer(50, new SkullVerticalListener(lblSkull,skull, healthSkull));
                    skull.setTimer(skullTimer);
                    break;
                case 1:
                    lblSkull = new JLabel();
                    healthSkull = new JProgressBar(0,500);
                    rnd = new Random().nextInt(250 - 180 + 1) + 180;
                    skull = new Skull("skull", healthSkull, lblSkull,null,"sube",IMG_SKULL_UP,ANCHO_CHARACTERS,ALTO_CHARACTERS,rnd,200,skullSpeed,700);
                    setSkull(skull, lblSkull, skullsList);
                    skullTimer = new Timer(50, new SkullVerticalListener(lblSkull,skull, healthSkull));
                    skull.setTimer(skullTimer);
                    break;
                case 2:
                    lblSkull = new JLabel();
                    healthSkull = new JProgressBar(0,500);
                    rnd = new Random().nextInt(450 - 300 + 1) + 300;
                    skull = new Skull("skull", healthSkull, lblSkull,null,"baja",IMG_SKULL_DOWN,ANCHO_CHARACTERS,ALTO_CHARACTERS,rnd,200,skullSpeed,700);
                    setSkull(skull, lblSkull, skullsList);
                    skullTimer = new Timer(50, new SkullVerticalListener(lblSkull,skull, healthSkull));
                    skull.setTimer(skullTimer);
                    break;
                case 3:
                    lblSkull = new JLabel();
                    healthSkull = new JProgressBar(0,500);
                    rnd = new Random().nextInt(700 - 500 + 1) + 550;
                    skull = new Skull("skull", healthSkull, lblSkull,null,"sube",IMG_SKULL_UP,ANCHO_CHARACTERS,ALTO_CHARACTERS,rnd,200,skullSpeed,700);
                    setSkull(skull, lblSkull, skullsList);
                    skullTimer = new Timer(50, new SkullVerticalListener(lblSkull,skull, healthSkull));
                    skull.setTimer(skullTimer);
                    break;
                case 4:
                    lblSkull = new JLabel();
                    healthSkull = new JProgressBar(0,500);
                    rnd = new Random().nextInt(900 - 800 + 1) + 800;
                    skull = new Skull("skull", healthSkull, lblSkull,null,"baja",IMG_SKULL_DOWN,ANCHO_CHARACTERS,ALTO_CHARACTERS,rnd,200,skullSpeed,800);
                    setSkull(skull, lblSkull, skullsList);
                    skullTimer = new Timer(50, new SkullVerticalListener(lblSkull,skull, healthSkull));
                    skull.setTimer(skullTimer);
                    break;
                case 5:
                    lblSkull = new JLabel();
                    healthSkull = new JProgressBar(0,500);
                    rnd = new Random().nextInt(1100 - 1000 + 1) + 1000;
                    skull = new Skull("skull", healthSkull, lblSkull,null,"sube",IMG_SKULL_UP,ANCHO_CHARACTERS,ALTO_CHARACTERS,rnd,200,skullSpeed,500);
                    setSkull(skull, lblSkull, skullsList);
                    skullTimer = new Timer(50, new SkullVerticalListener(lblSkull,skull, healthSkull));
                    skull.setTimer(skullTimer);
                    break;
                case 6:
                    lblSkull = new JLabel();
                    healthSkull = new JProgressBar(0,500);
                    rnd = new Random().nextInt(105 - 90 + 1) + 90;
                    skull = new Skull("skull", healthSkull, lblSkull,null,"derecha",IMG_SKULL_RIGHT,ANCHO_CHARACTERS,ALTO_CHARACTERS,1000,rnd,skullSpeed,900);
                    setSkull(skull, lblSkull, skullsList);
                    skullTimer = new Timer(50, new SkullHorizontalListener(lblSkull,skull, healthSkull));
                    skull.setTimer(skullTimer);
                    break;
                case 7:
                    lblSkull = new JLabel();
                    healthSkull = new JProgressBar(0,500);
                    rnd = new Random().nextInt(200 - 150 + 1) + 150;
                    skull = new Skull("skull", healthSkull, lblSkull,null,"izquierda",IMG_SKULL_LEFT,ANCHO_CHARACTERS,ALTO_CHARACTERS,1000,rnd,skullSpeed,900);
                    setSkull(skull, lblSkull, skullsList);
                    skullTimer = new Timer(50, new SkullHorizontalListener(lblSkull,skull, healthSkull));
                    skull.setTimer(skullTimer);
                    break;
                case 8:
                    lblSkull = new JLabel();
                    healthSkull = new JProgressBar(0,500);
                    rnd = new Random().nextInt(330 - 250 + 1) + 250;
                    skull = new Skull("skull", healthSkull, lblSkull,null,"izquierda",IMG_SKULL_LEFT,ANCHO_CHARACTERS,ALTO_CHARACTERS,1000,rnd,skullSpeed,1200);
                    setSkull(skull, lblSkull, skullsList);
                    skullTimer = new Timer(50, new SkullHorizontalListener(lblSkull,skull, healthSkull));
                    skull.setTimer(skullTimer);
                    break;
                case 9:
                    lblSkull = new JLabel();
                    healthSkull = new JProgressBar(0,500);
                    rnd = new Random().nextInt(430 - 370 + 1) + 370;
                    skull = new Skull("skull", healthSkull, lblSkull,null,"derecha",IMG_SKULL_RIGHT,ANCHO_CHARACTERS,ALTO_CHARACTERS,1000,rnd,skullSpeed,1200);
                    setSkull(skull, lblSkull, skullsList);
                    skullTimer = new Timer(50, new SkullHorizontalListener(lblSkull,skull, healthSkull));
                    skull.setTimer(skullTimer);
                    break;
                case 10:
                    lblSkull = new JLabel();
                    healthSkull = new JProgressBar(0,500);
                    rnd = new Random().nextInt(490 - 470 + 1) + 470;
                    skull = new Skull("skull", healthSkull, lblSkull,null,"derecha",IMG_SKULL_RIGHT,ANCHO_CHARACTERS,ALTO_CHARACTERS,1000,rnd,skullSpeed,2000);
                    setSkull(skull, lblSkull, skullsList);
                    skullTimer = new Timer(50, new SkullHorizontalListener(lblSkull,skull, healthSkull));
                    skull.setTimer(skullTimer);
                    break;
            }

            skullsObjList.add(skull);
        }

        startSkulls(skullsObjList);
    }

    private void startSkulls(ArrayList<Skull> skullsObjList) {

        for (Skull skull : skullsObjList) {
            skull.start();
        }
    }


    private class SkullVerticalListener implements ActionListener {

        JLabel lblSkull;
        Skull skull;
        JProgressBar healthSkull;

        public SkullVerticalListener(JLabel lblSkull, Skull skull,JProgressBar healthSkull) {

            this.lblSkull = lblSkull;
            this.skull = skull;
            this.healthSkull = healthSkull;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            boolean choque = false;

            if (this.skull.getDirection().equals("sube")) {
                choque = moveUp(this.lblSkull,this.skull);
                if(choque){
                    this.skull.setDirection("baja");
                    icon = setIcon(IMG_SKULL_DOWN,this.lblSkull);
                    this.lblSkull.setIcon(icon);
                }
            }

            if (this.skull.getDirection().equals("baja")) {
                choque = moveDown(this.lblSkull,this.skull);
                if(choque){
                    this.skull.setDirection("sube");
                    icon = setIcon(IMG_SKULL_UP,this.lblSkull);
                    this.lblSkull.setIcon(icon);
                }
            }
        }
    }

    private class SkullHorizontalListener implements ActionListener {

        JLabel lblSkull;
        Skull skull;
        JProgressBar healthSkull;

        public SkullHorizontalListener(JLabel lblSkull, Skull skull,JProgressBar healthSkull) {

            this.lblSkull = lblSkull;
            this.skull = skull;
            this.healthSkull = healthSkull;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (this.skull.getDirection().equals("derecha")) {
                choque = moveRight(this.lblSkull,this.skull);
                if(choque){
                    this.skull.setDirection("izquierda");
                    icon = setIcon(IMG_SKULL_LEFT,this.lblSkull);
                    this.lblSkull.setIcon(icon);
                }
            }

            if (this.skull.getDirection().equals("izquierda")) {
                choque = moveLeft(this.lblSkull,this.skull);
                if(choque){
                    this.skull.setDirection("derecha");
                    icon = setIcon(IMG_SKULL_RIGHT,this.lblSkull);
                    this.lblSkull.setIcon(icon);
                }
            }
        }
    }


    private void keyEvents() {

        lblPlayer.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    icon = setIcon(IMG_DERECHA_PARADO,lblPlayer);
                    lblPlayer.setIcon(icon);
                    player.setDirection("");
                }
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    icon = setIcon(IMG_IZQUIERDA_PARADO,lblPlayer);
                    lblPlayer.setIcon(icon);
                    player.setDirection("");
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    icon = setIcon(IMG_ESPALDA_PARADO,lblPlayer);
                    lblPlayer.setIcon(icon);
                    player.setDirection("");
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    icon = setIcon(IMG_CARA_PARADO,lblPlayer);
                    lblPlayer.setIcon(icon);
                    player.setDirection("");
                }

                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    spacePressed = false;
                }

                if(e.getKeyCode() == KeyEvent.VK_CONTROL){
                    ctrlPressed = false;
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    if(!player.getDirection().equals("derecha")){
                        icon = setIcon(IMG_DERECHA_MOVIMIENTO,lblPlayer);
                        lblPlayer.setIcon(icon);
                        player.setDirection("derecha");
                        direction = "right";
                    }
                    moveRight(lblPlayer,null);
                }
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    if(!player.getDirection().equals("izquierda")){
                        icon = setIcon(IMG_IZQUIERDA_MOVIMIENTO,lblPlayer);
                        lblPlayer.setIcon(icon);
                        player.setDirection("izquierda");
                        direction = "left";
                    }
                    moveLeft(lblPlayer,null);
                }
                if(e.getKeyCode() == KeyEvent.VK_UP){
                    if(!player.getDirection().equals("sube")){
                        icon = setIcon(IMG_ESPALDA_MOVIMIENTO,lblPlayer);
                        lblPlayer.setIcon(icon);
                        player.setDirection("sube");
                        direction = "up";
                    }
                    moveUp(lblPlayer,null);
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    if(!player.getDirection().equals("baja")){
                        icon = setIcon(IMG_CARA_MOVIMIENTO,lblPlayer);
                        lblPlayer.setIcon(icon);
                        player.setDirection("baja");
                        direction = "down";
                    }
                    moveDown(lblPlayer,null);
                }

                if(e.getKeyCode() == KeyEvent.VK_SPACE && !spacePressed){

                    try {
                        Thread.sleep(5);
                        //lblPlayer.getInputMap().put(KeyStroke.getKeyStroke((char) KeyEvent.VK_SPACE), "fuego");
                        //lblPlayer.getActionMap().put("fuego", new LanzaBolaFuego(playerType));
                        attack();
                        spacePressed = true;
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                if(e.getKeyCode() == KeyEvent.VK_CONTROL){

                    if(player.getLifes() > 0 && !ctrlPressed){
                        specialAttack();
                        ctrlPressed = true;
                    }
                }
            }
        });

    }

    private void attack(){

        if(shots < 4){
            JLabel lblFuego = new JLabel();
            URL url = null;
            int width = 0;
            int height = 0;
            int posX = 0;
            int posY = 0;

            if(playerType.equals("wizard")){
                IMG_RIGHT = "img/wizard/fuego_right.png";
                IMG_LEFT = "img/wizard/fuego_left.png";
                IMG_UP = "img/wizard/fuego_up.png";
                IMG_DOWN = "img/wizard/fuego_down.png";
            }

            if(playerType.equals("warrior")){
                IMG_RIGHT = "img/warrior/sword_right.png";
                IMG_LEFT = "img/warrior/sword_left.png";
                IMG_UP = "img/warrior/sword_up.png";
                IMG_DOWN = "img/warrior/sword_down.png";
            }

            if(playerType.equals("priest")){
                IMG_RIGHT = "img/priest/energia.png";
                IMG_LEFT = "img/priest/energia.png";
                IMG_UP = "img/priest/energia.png";
                IMG_DOWN = "img/priest/energia.png";
            }

            if(direction.equals("up")){
                url = getClass().getResource(IMG_UP);
                width = 22;
                height = 50;
                posX = lblPlayer.getX() + (lblPlayer.getWidth()/4);
                posY = lblPlayer.getY();
            }
            if(direction.equals("right")){
                url = getClass().getResource(IMG_RIGHT);
                width = 50;
                height = 22;
                posX = lblPlayer.getX()+(lblPlayer.getWidth()/2);
                posY = lblPlayer.getY() + (lblPlayer.getHeight()/3);
            }
            if(direction.equals("down")){
                url = getClass().getResource(IMG_DOWN);
                width = 22;
                height = 50;
                posX = lblPlayer.getX() + (lblPlayer.getWidth()/4);
                posY = lblPlayer.getY() + (lblPlayer.getHeight()/3);
            }
            if(direction.equals("left")){
                url = getClass().getResource(IMG_LEFT);
                width = 50;
                height = 22;
                posX = lblPlayer.getX();
                posY = lblPlayer.getY() + (lblPlayer.getHeight()/3);
            }


            ImageIcon iconFuego = new ImageIcon(url);
            Icon icon = new ImageIcon(iconFuego.getImage());
            lblFuego.setToolTipText("disparo");
            lblFuego.setIcon(icon);
            lblFuego.setBounds(posX, posY, width, height);

            gamePanel.add(lblFuego, 1);

            if(direction.equals("up")){
                Timer bolaFuegoTimer = new Timer(50,new BolaFuegoListener(lblFuego,"up"));
                bolaFuegoTimer.start();
                fireBalls.put(lblFuego,bolaFuegoTimer);
            }
            if(direction.equals("right")){
                Timer bolaFuegoTimer = new Timer(50,new BolaFuegoListener(lblFuego,"right"));
                bolaFuegoTimer.start();
                fireBalls.put(lblFuego,bolaFuegoTimer);
            }
            if(direction.equals("down")){
                Timer bolaFuegoTimer = new Timer(50,new BolaFuegoListener(lblFuego,"down"));
                bolaFuegoTimer.start();
                fireBalls.put(lblFuego,bolaFuegoTimer);
            }
            if(direction.equals("left")){
                Timer bolaFuegoTimer = new Timer(50,new BolaFuegoListener(lblFuego,"left"));
                bolaFuegoTimer.start();
                fireBalls.put(lblFuego,bolaFuegoTimer);
            }
            shots++;
        }
    }

    private void specialAttack() {

        final String IMG_EXPLOSION = "img/dungeon/explosion.gif";
        int xMin = lblPlayer.getX() - 100;
        int xMax = lblPlayer.getX() + 100;
        int yMin = lblPlayer.getY() - 100;
        int yMax = lblPlayer.getY() + 100;
        int yMinIncrement = yMin;
        ArrayList<JLabel> labels = new ArrayList<>();
        Map<JLabel,Skull> targets = new HashMap<>();
        ArrayList<Component> components = new ArrayList<>();

        while(xMin < xMax){
            while(yMinIncrement < yMax){
                if(gamePanel.getComponentAt(xMin,yMinIncrement) != null){
                    components.add(gamePanel.getComponentAt(xMin,yMinIncrement));
                }
                yMinIncrement += 15;
            }
            xMin += 15;
            yMinIncrement = yMin;
        }

        for (Component component : components) {
            if(component instanceof JLabel)
                labels.add((JLabel) component);
        }

        for (JLabel label : labels) {
            if(label.getToolTipText().equals("skull")){
                if(skullsObjList.contains(new Skull(label))){
                    int index = skullsObjList.indexOf(new Skull(label));
                    targets.put(label,skullsObjList.get(index));
                }
            }
        }

        Icon icon;
        icon = setIcon(IMG_EXPLOSION, lblPlayer);
        lblPlayer.setIcon(icon);
        healthPlayer.setVisible(false);

        for (Map.Entry<JLabel, Skull> entry : targets.entrySet()) {
            destroy(entry.getValue(),entry.getKey());
        }


        Timer reloadPlayer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkDead(lblPlayer,null, true);
            }
        });

        reloadPlayer.setRepeats(false);
        reloadPlayer.start();

    }

    /*private class LanzaBolaFuego extends AbstractAction{

        private String type;

        public LanzaBolaFuego(String type){
            this.type = type;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
             if(shots < 4){
                JLabel lblFuego = new JLabel();
                 URL url = null;
                 int width = 0;
                 int height = 0;
                 int posX = 0;
                 int posY = 0;

                 if(this.type.equals("wizard")){
                     IMG_RIGHT = "img/wizard/fuego_right.png";
                     IMG_LEFT = "img/wizard/fuego_left.png";
                     IMG_UP = "img/wizard/fuego_up.png";
                     IMG_DOWN = "img/wizard/fuego_down.png";
                 }

                 if(this.type.equals("warrior")){
                     IMG_RIGHT = "img/warrior/sword_right.png";
                     IMG_LEFT = "img/warrior/sword_left.png";
                     IMG_UP = "img/warrior/sword_up.png";
                     IMG_DOWN = "img/warrior/sword_down.png";
                 }

                 if(this.type.equals("priest")){
                     IMG_RIGHT = "img/priest/energia.png";
                     IMG_LEFT = "img/priest/energia.png";
                     IMG_UP = "img/priest/energia.png";
                     IMG_DOWN = "img/priest/energia.png";
                 }

                 if(direction.equals("up")){
                     url = getClass().getResource(IMG_UP);
                     width = 22;
                     height = 50;
                     posX = lblPlayer.getX() + (lblPlayer.getWidth()/4);
                     posY = lblPlayer.getY();
                 }
                 if(direction.equals("right")){
                     url = getClass().getResource(IMG_RIGHT);
                     width = 50;
                     height = 22;
                     posX = lblPlayer.getX()+(lblPlayer.getWidth()/2);
                     posY = lblPlayer.getY() + (lblPlayer.getHeight()/3);
                 }
                 if(direction.equals("down")){
                     url = getClass().getResource(IMG_DOWN);
                     width = 22;
                     height = 50;
                     posX = lblPlayer.getX() + (lblPlayer.getWidth()/4);
                     posY = lblPlayer.getY() + (lblPlayer.getHeight()/3);
                 }
                 if(direction.equals("left")){
                     url = getClass().getResource(IMG_LEFT);
                     width = 50;
                     height = 22;
                     posX = lblPlayer.getX();
                     posY = lblPlayer.getY() + (lblPlayer.getHeight()/3);
                 }


                ImageIcon iconFuego = new ImageIcon(url);
                Icon icon = new ImageIcon(iconFuego.getImage());
                lblFuego.setToolTipText("disparo");
                lblFuego.setIcon(icon);
                lblFuego.setBounds(posX, posY, width, height);

                gamePanel.add(lblFuego, 1);

                if(direction.equals("up")){
                    Timer bolaFuegoTimer = new Timer(50,new BolaFuegoListener(lblFuego,"up"));
                    bolaFuegoTimer.start();
                    fireBalls.put(lblFuego,bolaFuegoTimer);
                }
                if(direction.equals("right")){
                    Timer bolaFuegoTimer = new Timer(50,new BolaFuegoListener(lblFuego,"right"));
                    bolaFuegoTimer.start();
                    fireBalls.put(lblFuego,bolaFuegoTimer);
                }
                if(direction.equals("down")){
                    Timer bolaFuegoTimer = new Timer(50,new BolaFuegoListener(lblFuego,"down"));
                    bolaFuegoTimer.start();
                    fireBalls.put(lblFuego,bolaFuegoTimer);
                }
                if(direction.equals("left")){
                    Timer bolaFuegoTimer = new Timer(50,new BolaFuegoListener(lblFuego,"left"));
                    bolaFuegoTimer.start();
                    fireBalls.put(lblFuego,bolaFuegoTimer);
                }
                shots++;
            }
        }
    }*/

    private class BolaFuegoListener implements ActionListener{

        JLabel lblFuego;
        String direction;

        public BolaFuegoListener(JLabel lblFuego, String direction){
            this.lblFuego = lblFuego;
            this.direction = direction;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(direction.equals("up")){
                fireX = lblFuego.getX();
                fireY = lblFuego.getY() - 15;
            }
            if(direction.equals("right")){
                fireX = lblFuego.getX() + 15;
                fireY = lblFuego.getY();
            }
            if(direction.equals("down")){
                fireX = lblFuego.getX();
                fireY = lblFuego.getY() + 15;
            }
            if(direction.equals("left")){
                fireX = lblFuego.getX() - 15;
                fireY = lblFuego.getY();
            }

            lblFuego.setLocation(fireX,fireY);


            if(lblFuego.getLocation().x > (gamePanel.getWidth() - ANCHO_LABELS) ||
                    lblFuego.getLocation().x <= 0 ||
                    lblFuego.getLocation().y > (gamePanel.getHeight() - ALTO_LABELS) ||
                    lblFuego.getLocation().y <= 0){
                gamePanel.remove(lblFuego);
                shots--;
                fireBalls.get(lblFuego).stop();
                fireBalls.remove(lblFuego);
            }
            lblPlayer.requestFocus();
        }
    }

    private boolean moveRight(JLabel personaje, Skull skull) {
        int x = 0;
        int y = 0;
        int idColision = -1;
        int speed = 0;
        ArrayList<JLabel> objects = new ArrayList<>();

        decreaseLife();
        checkTime();

        choque = false;

        if(!personaje.getToolTipText().equals("skull")){
            speed = player.getSpeed();
        }else{
            speed = skull.getSpeed();
        }

        x = personaje.getX() + speed;
        y = personaje.getY();

        for (Dungeon valor : colisiones) {
            idColision++;

            antesX = ((x + personaje.getWidth()) < valor.label.getX());
            despuesX = (x >= (valor.label.getX() + ANCHO_LABELS));
            antesY = ((y + personaje.getHeight()) <= valor.label.getY());
            despuesY = (y >= (valor.label.getY() + ALTO_LABELS));

            if (antesX || antesY) {
                choque = false;
                if(personaje.getToolTipText().equals("skull")){
                    choque = checkSkull(personaje, skull, speed, skullsList);
                }
            }else if (despuesX || despuesY) {
                choque = false;
                if(personaje.getToolTipText().equals("skull")){
                    choque = checkSkull(personaje, skull, speed, skullsList);
                }
            }else{
                choque = true;
                if(!antesX){
                    int diff = (valor.label.getX() - (personaje.getX() + personaje.getWidth()));
                    x = personaje.getX() + diff;
                    personaje.setLocation(x, y);
                }

                if(valor.label.getToolTipText().equals("exit") && gold >= 50){
                    JOptionPane.showMessageDialog(null,"Fin del juego.");
                    gamePanel.remove(lblPlayer);
                    gamePanel.remove(healthPlayer);
                    gamePanel.repaint();

                    duration = getDuration(horaInicio);
                    String mensajeEstadisticas =  player.getInfo() + "," + String.format("%02d", duration);
                    saveRanking(mensajeEstadisticas,RANKING);

                    timeChecker.stop();
                    int input = JOptionPane.showConfirmDialog(null, "Quieres volver a jugar?", "Jugar de nuevo",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    frame.dispose();
                    if(input == 0){
                        String[] args = {};
                        SelectType.main(args);
                    }else{
                        System.exit(0);
                    }
                }

                if(!personaje.getToolTipText().equals("skull")){
                    if(valor.type.equals("gold")){
                        int rnd = 0;

                        while(!items[rnd].getToolTipText().equals("terra")) {
                            rnd = new Random().nextInt(items.length);
                        }
                        addItem(itemCoins,items[rnd].getX(),items[rnd].getY());
                        goldItem.setLabel(itemCoins);
                    }else if(!valor.label.getToolTipText().equals("mur") && !valor.label.getToolTipText().equals("gold") && !valor.label.getToolTipText().equals("exit")){
                        if(player.getObjects() != null){
                            objects = player.getObjects();
                        }

                        if(valor.type.equals("potion") && player.isPoisoned()){
                            player.setPoisoned(false);
                            lblPoisoned.setText("");
                            resetPlayer(red);
                            healthPlayer.setVisible(false);
                            gamePanel.remove(valor.label);
                        }else{
                            objects.add(valor.label);
                        }

                        player.setObjects(objects);
                        refreshObjectsPanel();
                    }

                    if(valor.type.equals("gold")){
                        gold += 10;
                        player.setGold(gold);
                        lblGoldInfo.setText(String.valueOf(player.getGold()));
                        checkGold();
                    }

                    if(!valor.type.equals("gold") && !valor.type.equals("mur")){
                        colisiones.remove(idColision);
                    }

                    gamePanel.repaint();
                }

                break;
            }
        }


        if (!choque) {
            if(!personaje.getToolTipText().equals("skull")) {
                updatePlayerPosition(personaje,x,y);
            }else{
                personaje.setLocation(x, y);
                skull.getHealthBar().setLocation(x, y - 10);
                //healthSkull.setLocation(x, y - 10);
            }
        }

        return choque;
    }

    private boolean moveLeft(JLabel personaje, Skull skull) {
        int x = 0;
        int y = 0;
        int idColision = -1;
        int speed = 0;
        ArrayList<JLabel> objects = new ArrayList<>();

        decreaseLife();
        checkTime();

        choque = false;

        if(!personaje.getToolTipText().equals("skull")){
            speed = player.getSpeed();
        }else{
            speed = skull.getSpeed();
        }

        x = personaje.getX() - speed;
        y = personaje.getY();

        for (Dungeon valor : colisiones) {
            idColision++;


            antesX = ((x + personaje.getWidth()) < valor.label.getX());
            despuesX = (x >= (valor.label.getX() + ANCHO_LABELS));
            antesY = ((y + personaje.getHeight()) <= valor.label.getY());
            despuesY = (y >= (valor.label.getY() + ALTO_LABELS));

            if (antesX || antesY) {
                choque = false;
                if(personaje.getToolTipText().equals("skull")){
                    choque = checkSkull(personaje, skull, speed, skullsList);
                }
            }else if (despuesX || despuesY) {
                choque = false;
                if(personaje.getToolTipText().equals("skull")){
                    choque = checkSkull(personaje, skull, speed, skullsList);
                }
            }else{
                choque = true;
                if(!despuesX){
                    x = personaje.getX() + (valor.label.getX() + ANCHO_LABELS) - personaje.getX();
                    personaje.setLocation(x, y);
                }

                if(!personaje.getToolTipText().equals("skull")){
                    if(valor.type.equals("gold")){
                        int rnd = 0;

                        while(!items[rnd].getToolTipText().equals("terra")) {
                            rnd = new Random().nextInt(items.length);
                        }
                        addItem(itemCoins,items[rnd].getX(),items[rnd].getY());
                        goldItem.setLabel(itemCoins);
                    }else if(!valor.label.getToolTipText().equals("mur") && !valor.label.getToolTipText().equals("gold") && !valor.label.getToolTipText().equals("exit")){
                        if(player.getObjects() != null){
                            objects = player.getObjects();
                        }

                        if(valor.type.equals("potion") && player.isPoisoned()){
                            player.setPoisoned(false);
                            lblPoisoned.setText("");
                            resetPlayer(red);
                            healthPlayer.setVisible(false);
                            gamePanel.remove(valor.label);
                        }else{
                            objects.add(valor.label);
                        }

                        player.setObjects(objects);
                        refreshObjectsPanel();
                    }


                    if(valor.type.equals("gold")){
                        gold += 10;
                        player.setGold(gold);
                        lblGoldInfo.setText(String.valueOf(player.getGold()));
                        checkGold();
                    }


                    if(!valor.type.equals("gold") && !valor.type.equals("mur")){
                        colisiones.remove(idColision);
                    }

                    gamePanel.repaint();
                }

                break;
            }
        }

        if(player.getCoordX() <= 0){
            lblPlayer.setLocation(1,lblPlayer.getY());
        }

        if (!choque) {
            if(!personaje.getToolTipText().equals("skull")) {
                updatePlayerPosition(personaje,x,y);
            }else{
                personaje.setLocation(x, y);
                skull.getHealthBar().setLocation(x, y - 10);
            }
        }

        return choque;
    }

    private boolean moveUp(JLabel personaje, Skull skull) {
        int x = 0;
        int y = 0;
        int idColision = -1;
        int speed = 0;
        ArrayList<JLabel> objects = new ArrayList<>();

        decreaseLife();
        checkTime();

        choque = false;

        if(!personaje.getToolTipText().equals("skull")){
            speed = player.getSpeed();
        }else{
            speed = skull.getSpeed();
        }

        x = personaje.getX();
        y = personaje.getY() - speed;



        for (Dungeon valor : colisiones){
            idColision++;


            antesX = ((x + personaje.getWidth()) <= valor.label.getX());
            despuesX = (x >= (valor.label.getX() + ANCHO_LABELS));
            antesY = ((y + personaje.getHeight()) <= valor.label.getY());
            despuesY = (y >= (valor.label.getY() + ALTO_LABELS));

            if (antesX || antesY) {
                choque = false;
                if(personaje.getToolTipText().equals("skull")){
                    choque = checkSkull(personaje, skull, speed, skullsList);
                }
            }else if (despuesX || despuesY) {
                choque = false;
                if(personaje.getToolTipText().equals("skull")){
                    choque = checkSkull(personaje, skull, speed, skullsList);
                }
            }else{
                choque = true;
                if(!despuesY){
                    y = personaje.getY() + (valor.label.getY() + ANCHO_LABELS) - personaje.getY();
                    personaje.setLocation(x, y);
                }

                if(!personaje.getToolTipText().equals("skull")){
                    if(valor.type.equals("gold")){
                        int rnd = 0;

                        while(!items[rnd].getToolTipText().equals("terra")) {
                            rnd = new Random().nextInt(items.length);
                        }
                        addItem(itemCoins,items[rnd].getX(),items[rnd].getY());
                        goldItem.setLabel(itemCoins);
                    }else if(!valor.label.getToolTipText().equals("mur") && !valor.label.getToolTipText().equals("gold") && !valor.label.getToolTipText().equals("exit")){
                        if(player.getObjects() != null){
                            objects = player.getObjects();
                        }

                        if(valor.type.equals("potion") && player.isPoisoned()){
                            player.setPoisoned(false);
                            lblPoisoned.setText("");
                            resetPlayer(red);
                            healthPlayer.setVisible(false);
                            gamePanel.remove(valor.label);
                        }else{
                            objects.add(valor.label);
                        }

                        player.setObjects(objects);
                        refreshObjectsPanel();
                    }

                    if(valor.type.equals("gold")){
                        gold += 10;
                        player.setGold(gold);
                        lblGoldInfo.setText(String.valueOf(player.getGold()));
                        checkGold();
                    }


                    if(!valor.type.equals("gold") && !valor.type.equals("mur")){
                        colisiones.remove(idColision);
                    }

                    gamePanel.repaint();
                }

                break;
            }
        }


        if (!choque) {
            if(!personaje.getToolTipText().equals("skull")) {
                updatePlayerPosition(personaje,x,y);
            }else{
                personaje.setLocation(x, y);
                skull.getHealthBar().setLocation(x, y - 10);
            }
        }

        return choque;
    }

    private boolean moveDown(JLabel personaje, Skull skull) {
        int x = 0;
        int y = 0;
        int idColision = -1;
        int speed = 0;
        ArrayList<JLabel> objects = new ArrayList<>();

        decreaseLife();
        checkTime();


        choque = false;

        if(!personaje.getToolTipText().equals("skull")){
            speed = player.getSpeed();
        }else{
            speed = skull.getSpeed();
        }

        x = personaje.getX();
        y = personaje.getY() + speed;

        for (Dungeon valor : colisiones){
            idColision++;


            antesX = ((x + personaje.getWidth()) <= valor.label.getX());
            despuesX = (x >= (valor.label.getX() + ANCHO_LABELS));
            antesY = ((y + personaje.getHeight()) <= valor.label.getY());
            despuesY = (y >= (valor.label.getY() + ALTO_LABELS));

            if (antesX || antesY) {
                choque = false;
                if(personaje.getToolTipText().equals("skull")){
                    choque = checkSkull(personaje, skull, speed, skullsList);
                }
            }else if (despuesX || despuesY) {
                choque = false;
                if(personaje.getToolTipText().equals("skull")){
                    choque = checkSkull(personaje, skull, speed, skullsList);
                }
            }else{
                choque = true;

                if(!antesY){
                    int diff = ((valor.label.getY()) - (personaje.getY() + personaje.getHeight()));
                    y = personaje.getY() + diff;
                    personaje.setLocation(x, y);
                }

                if(!personaje.getToolTipText().equals("skull")){
                    if(valor.type.equals("gold")){
                        int rnd = 0;

                        while(!items[rnd].getToolTipText().equals("terra")) {
                            rnd = new Random().nextInt(items.length);
                        }
                        addItem(itemCoins,items[rnd].getX(),items[rnd].getY());
                        goldItem.setLabel(itemCoins);
                    }else if(!valor.label.getToolTipText().equals("mur") && !valor.label.getToolTipText().equals("gold") && !valor.label.getToolTipText().equals("exit")){
                        if(player.getObjects() != null){
                            objects = player.getObjects();
                        }

                        if(valor.type.equals("potion") && player.isPoisoned()){
                            player.setPoisoned(false);
                            lblPoisoned.setText("");
                            resetPlayer(red);
                            healthPlayer.setVisible(false);
                            gamePanel.remove(valor.label);
                        }else{
                            objects.add(valor.label);
                        }

                        player.setObjects(objects);
                        refreshObjectsPanel();
                    }

                    if(valor.type.equals("gold")){
                        gold += 10;
                        player.setGold(gold);
                        lblGoldInfo.setText(String.valueOf(player.getGold()));
                        checkGold();
                    }


                    if(!valor.type.equals("gold") && !valor.type.equals("mur")){
                        colisiones.remove(idColision);
                    }

                    gamePanel.repaint();
                }

                break;
            }
        }



            if (!choque) {
                if(!personaje.getToolTipText().equals("skull")) {
                    updatePlayerPosition(personaje,x,y);
                }else{
                    personaje.setLocation(x, y);
                    skull.getHealthBar().setLocation(x, y - 10);
                }
            }

        return choque;
    }

    private boolean checkSkull(JLabel personaje, Skull skull, int speed, ArrayList<JLabel> skullsList){

        int x = 0;
        int y = 0;

        x = personaje.getX();
        y = personaje.getY() + speed;

        antesX = ((x + personaje.getWidth()) < lblPlayer.getX());
        despuesX = (x >= (lblPlayer.getX() + lblPlayer.getWidth()));
        antesY = ((y + personaje.getHeight()) <= lblPlayer.getY());
        despuesY = (y >= (lblPlayer.getY() + lblPlayer.getHeight()));

        if (antesX) {
            choque = false;

            try{
                for (Map.Entry<JLabel, Timer> fireBall : fireBalls.entrySet()) {
                    int inicioX = personaje.getX();
                    int inicioY = personaje.getY();
                    int finX = personaje.getX() + personaje.getWidth();
                    int finY = personaje.getY() + personaje.getHeight();

                    if((((fireBall.getKey().getX() >= inicioX) && (fireBall.getKey().getX() <= finX)) || (((fireBall.getKey().getX() + fireBall.getKey().getWidth()) >= inicioX) && ((fireBall.getKey().getX() + fireBall.getKey().getWidth()) <= finX)))
                            &&
                       (((fireBall.getKey().getY() >= inicioY) && (fireBall.getKey().getY() <= finY)) || (((fireBall.getKey().getY() + fireBall.getKey().getHeight()) >= inicioY) && ((fireBall.getKey().getY() + fireBall.getKey().getHeight()) <= finY)))){

                            checkImpact(fireBall,personaje,skull);
                    }
                }
            }catch (ConcurrentModificationException cme){

            }


        }else if (antesY) {
            choque = false;

            try{
                for (Map.Entry<JLabel, Timer> fireBall : fireBalls.entrySet()) {
                    int inicioX = personaje.getX();
                    int inicioY = personaje.getY();
                    int finX = personaje.getX() + personaje.getWidth();
                    int finY = personaje.getY() + personaje.getHeight();

                    if((((fireBall.getKey().getX() >= inicioX) && (fireBall.getKey().getX() <= finX)) || (((fireBall.getKey().getX() + fireBall.getKey().getWidth()) >= inicioX) && ((fireBall.getKey().getX() + fireBall.getKey().getWidth()) <= finX)))
                            &&
                       (((fireBall.getKey().getY() >= inicioY) && (fireBall.getKey().getY() <= finY)) || (((fireBall.getKey().getY() + fireBall.getKey().getHeight()) >= inicioY) && ((fireBall.getKey().getY() + fireBall.getKey().getHeight()) <= finY)))){

                            checkImpact(fireBall,personaje,skull);
                    }
                }
            }catch (ConcurrentModificationException cme){

            }

        }else if (despuesX) {
            choque = false;

            try{
                for (Map.Entry<JLabel, Timer> fireBall : fireBalls.entrySet()) {
                    int inicioX = personaje.getX();
                    int inicioY = personaje.getY();
                    int finX = personaje.getX() + personaje.getWidth();
                    int finY = personaje.getY() + personaje.getHeight();

                    if((((fireBall.getKey().getX() >= inicioX) && (fireBall.getKey().getX() <= finX)) || (((fireBall.getKey().getX() + fireBall.getKey().getWidth()) >= inicioX) && ((fireBall.getKey().getX() + fireBall.getKey().getWidth()) <= finX)))
                            &&
                       (((fireBall.getKey().getY() >= inicioY) && (fireBall.getKey().getY() <= finY)) || (((fireBall.getKey().getY() + fireBall.getKey().getHeight()) >= inicioY) && ((fireBall.getKey().getY() + fireBall.getKey().getHeight()) <= finY)))){

                            checkImpact(fireBall,personaje,skull);
                    }
                }
            }catch(ConcurrentModificationException cme){

            }

        }else if (despuesY) {
            choque = false;

            try{
                for (Map.Entry<JLabel, Timer> fireBall : fireBalls.entrySet()) {
                    int inicioX = personaje.getX();
                    int inicioY = personaje.getY();
                    int finX = personaje.getX() + personaje.getWidth();
                    int finY = personaje.getY() + personaje.getHeight();

                    if((((fireBall.getKey().getX() >= inicioX) && (fireBall.getKey().getX() <= finX)) || (((fireBall.getKey().getX() + fireBall.getKey().getWidth()) >= inicioX) && ((fireBall.getKey().getX() + fireBall.getKey().getWidth()) <= finX)))
                            &&
                       (((fireBall.getKey().getY() >= inicioY) && (fireBall.getKey().getY() <= finY)) || (((fireBall.getKey().getY() + fireBall.getKey().getHeight()) >= inicioY) && ((fireBall.getKey().getY() + fireBall.getKey().getHeight()) <= finY)))){

                            checkImpact(fireBall,personaje,skull);
                    }
                }
            }catch (ConcurrentModificationException cme){

            }

        }else {
            choque = true;

            isPoisoned(skull.getHealthBar());

            checkDead(personaje, skull, false);
        }

        return choque;
    }


    private void checkImpact(Map.Entry<JLabel, Timer> fireBall, JLabel personaje, Skull skull) {

        try{
            fireBall.getValue().stop();
            skull.setLifePoints(skull.getLifePoints()-100);
            skull.getHealthBar().setValue(skull.getLifePoints());
            if(skull.getLifePoints() <= 0){
                skull.stop();
                gamePanel.remove(personaje);
                gamePanel.remove(skull.getHealthBar());
                try{
                    skullsList.remove(0);
                }catch (IndexOutOfBoundsException iobe){

                }
                seconds += 10;
            }
            gamePanel.remove(fireBall.getKey());
            fireBalls.remove(fireBall.getKey(),fireBall.getValue());
            gamePanel.validate();
            gamePanel.repaint();
            shots--;
            checkGold();

        }catch(NullPointerException npe){

        }
    }

    private void destroy(Skull skull, JLabel personaje) {



            skull.setLifePoints(0);
            if(skull.getLifePoints() <= 0){
                skull.stop();
                gamePanel.remove(personaje);
                gamePanel.remove(skull.getHealthBar());
                try{
                    skullsList.remove(0);
                }catch (IndexOutOfBoundsException iobe){

                }
                seconds += 10;
            }
            gamePanel.validate();
            gamePanel.repaint();
            checkGold();
    }

    private void isPoisoned(JProgressBar healthSkull) {

        if(!healthSkull.getForeground().equals(Color.RED)){
            if(player.isPoisoned()){
                player.setPoisoned(false);
                lblPoisoned.setText("");
            }else{
                player.setPoisoned(true);
                lblPoisoned.setText("Poisoned");
            }
        }else{
            player.setPoisoned(false);
            lblPoisoned.setText("");
        }
    }

    private void refreshObjectsPanel() {

        if(player.getObjects() != null) {
            for (JLabel object : player.getObjects()) {
                object.setPreferredSize(new Dimension(50,32));
                object.setSize(object.getPreferredSize());
                itemsPanel.add(object);
                itemsPanel.validate();
            }
        }
    }

    private void checkGold(){

        int x=0;
        int y=0;
        int id=0;
        int counter=0;
        int[] idToDelete = new int[2];
        int[] doors = new int[2];

        if(player.getGold() >= MIN_GOLD && skullsList.size() == 0){

            for (Dungeon row: colisiones) {
                if(row.label.getToolTipText() != null){
                    if(row.label.getToolTipText().equals("exit")){
                        idToDelete[id] = counter;
                        doors[id] = Integer.parseInt(row.label.getName());
                        id++;
                    }
                }
                counter++;
            }

            id = 1;

            for (int door : doors) {
                x = items[door].getX();
                y = items[door].getY();
                gamePanel.remove(items[door]);
                items[door] = sueloItem.create(door,IMG_SUEL_P1);
                items[door].setLocation(x,y);
                gamePanel.add(items[door]);
                gamePanel.repaint();
                id--;
            }
        }
    }

    private void checkDead(JLabel personaje, Skull skull, boolean specialAttack){


        boolean potion = false;
        int idPotion = 0;
        boolean sword = false;
        int idSword = 0;
        boolean mitra = false;
        int idMitra = 0;
        int counter = 0;
        icon = setIcon(IMG_DERECHA_PARADO,lblPlayer);
        JLabel lblPotion = new JLabel();
        JLabel lblSword = new JLabel();
        JLabel lblMitra = new JLabel();
        ArrayList<JLabel> objects = new ArrayList<>();

        if(player.getObjects() != null){
            for (JLabel object: player.getObjects()) {
                if(object.getToolTipText().equals("potion")){
                    potion = true;
                    idPotion = counter;
                    lblPotion = object;
                }
                if(object.getToolTipText().equals("sword")){
                    sword = true;
                    idSword = counter;
                    lblSword = object;
                }
                if(object.getToolTipText().equals("mitra")){
                    mitra = true;
                    idMitra = counter;
                    lblMitra = object;
                }
                counter++;
            }
        }

        if(potion && lblPlayer.getToolTipText().equals("wizard") && !specialAttack){
            objects = player.getObjects();
            objects.remove(idPotion);
            resetPlayer(red);
            healthPlayer.setVisible(false);
            player.setObjects(objects);
            lblLifes.setText(String.valueOf(player.getLifes()));
            if(player.isPoisoned()){
                player.setPoisoned(false);
                lblPoisoned.setText("");
            }
            itemsPanel.remove(lblPotion);
            relocatePlayer(icon);
        }else if(sword && lblPlayer.getToolTipText().equals("warrior") && !specialAttack){
            lblLifes.setText(String.valueOf(player.getLifes()));
            objects = player.getObjects();
            objects.remove(idSword);

            if(potion){
                resetPlayer(red);
                healthPlayer.setVisible(false);
            }

            player.setObjects(objects);
            itemsPanel.remove(lblSword);
            // Cambio el location de la calavera muerta ya que con el remove no lo está quitando y sigue detectando colisión
            personaje.setLocation(0,0);
            skull.stop();
            gamePanel.remove(personaje);
            gamePanel.validate();
            gamePanel.repaint();
        }else if(mitra && lblPlayer.getToolTipText().equals("priest") && !specialAttack) {
            lblLifes.setText(String.valueOf(player.getLifes()));
            objects = player.getObjects();
            objects.remove(idMitra);

            if (potion) {
                resetPlayer(red);
                healthPlayer.setVisible(false);
            }

            player.setObjects(objects);
            itemsPanel.remove(lblMitra);
            relocatePlayer(icon);
        }else if(specialAttack){
            player.setLifes(player.getLifes()-1);
            lblLifes.setText(String.valueOf(player.getLifes()));
            relocatePlayer(icon);
            Icon icon;
            icon = setIcon(IMG_CARA_PARADO, lblPlayer);
            lblPlayer.setIcon(icon);
            healthPlayer.setVisible(false);

            if(player.isPoisoned()){
                player.setPoisoned(false);
                lblPoisoned.setText("");
            }
        }else{
            if(!player.isPoisoned()) {
                player.setLifes(player.getLifes() - 1);
            }
            lblLifes.setText(String.valueOf(player.getLifes()));
            if(player.isPoisoned()){
                resetPlayer(purple);
                healthPlayer.setVisible(true);
            }else {
                resetPlayer(red);
                healthPlayer.setVisible(false);
            }
            relocatePlayer(icon);
        }

        itemsPanel.repaint();
        refreshObjectsPanel();
        gamePanel.repaint();

        if(player.getLifes() == 0){
            JOptionPane.showMessageDialog(null, "No te quedan mas vidas. Fin del juego");

            duration = getDuration(horaInicio);
            String mensajeEstadisticas = player.getInfo() + "," + String.format("%02d", duration);
            saveRanking(mensajeEstadisticas,RANKING);

            timeChecker.stop();

            JLabel exorcista = new JLabel();;

            exorcista.setPreferredSize(gamePanel.getPreferredSize());
            exorcista.setSize(gamePanel.getPreferredSize());

            String IMG_EXOR = "img/dungeon/exorcista.gif";
            urlPlayer = getClass().getResource(IMG_EXOR);
            iconPlayer = new ImageIcon(urlPlayer);
            icon = new ImageIcon(iconPlayer.getImage().getScaledInstance(frame.getWidth(),frame.getHeight(),Image.SCALE_DEFAULT));

            exorcista.setIcon(icon);

            gamePanel.add(exorcista,1);

            proceed = new Timer(1500,new RestartListener(exorcista));
            proceed.start();
        }

        if(seconds == 0){
            JOptionPane.showMessageDialog(null, "No queda mas tiempo. Fin del juego");

            duration = getDuration(horaInicio);
            String mensajeEstadisticas = player.getInfo() + "," + String.format("%02d", duration);
            saveRanking(mensajeEstadisticas,RANKING);

            int input = JOptionPane.showConfirmDialog(null, "Quieres volver a jugar?", "Jugar de nuevo",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            frame.dispose();
            if(input == 0){
                String[] args = {};
                SelectType.main(args);
            }else{
                System.exit(0);
            }
        }
    }

    private class RestartListener implements ActionListener{

        JLabel exor;

        public  RestartListener(JLabel exor){
            this.exor = exor;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            int input = JOptionPane.showConfirmDialog(null, "Quieres volver a jugar?", "Jugar de nuevo",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            frame.dispose();
            gamePanel.remove(exor);
            gamePanel.repaint();
            if(input == 0){
                String[] args = {};
                SelectType.main(args);
            }else{
                System.exit(0);
            }

            proceed.stop();
        }
    }

    private void resetPlayer(String color) {

        player.setLifePoints(DEFAULT_LIFE);
        healthPlayer.setValue(player.getLifePoints());
        changeProgressBarColor(color);
    }

    private void changeProgressBarColor(String color) {

        if(color.equals(red)){
            healthPlayer.setForeground(Color.RED);
        }else if (color.equals(purple)){
            healthPlayer.setForeground(Color.decode(purple));
        }
    }

    private Icon setIcon(String img, JLabel item){

        urlPlayer = getClass().getResource(img);
        iconPlayer = new ImageIcon(urlPlayer);
        icon = new ImageIcon(iconPlayer.getImage().getScaledInstance(item.getWidth(),item.getHeight(),Image.SCALE_DEFAULT));

        return icon;
    }

    private void setPlayer(String type, String name){

        if(type.equals("warrior")){
            refreshTime = 10;
            player = new Warrior(name, type,5,3,1,65,"", 180,DEFAULT_LIFE,false);
            IMG_CARA_PARADO = "img/warrior/warrior_down_parado.png";
            IMG_DERECHA_PARADO = "img/warrior/warrior_right_parado.png";
            IMG_IZQUIERDA_PARADO = "img/warrior/warrior_left_parado.png";
            IMG_ESPALDA_PARADO = "img/warrior/warrior_up_parado.png";

            IMG_CARA_MOVIMIENTO = "img/warrior/warrior_down_movimiento.gif";
            IMG_ESPALDA_MOVIMIENTO = "img/warrior/warrior_up_movimiento.gif";
            IMG_DERECHA_MOVIMIENTO = "img/warrior/warrior_right_movimiento.gif";
            IMG_IZQUIERDA_MOVIMIENTO = "img/warrior/warrior_left_movimiento.gif";
        }else if(type.equals("wizard")){
            refreshTime = 5;
            player = new Wizard(name, type,3,7,1,65,"", 70,DEFAULT_LIFE,false);
            IMG_CARA_PARADO = "img/wizard/wizard_down_parado.png";
            IMG_DERECHA_PARADO = "img/wizard/wizard_right_parado.png";
            IMG_IZQUIERDA_PARADO = "img/wizard/wizard_left_parado.png";
            IMG_ESPALDA_PARADO = "img/wizard/wizard_up_parado.png";

            IMG_CARA_MOVIMIENTO = "img/wizard/wizard_down_movimiento.gif";
            IMG_ESPALDA_MOVIMIENTO = "img/wizard/wizard_up_movimiento.gif";
            IMG_DERECHA_MOVIMIENTO = "img/wizard/wizard_right_movimiento.gif";
            IMG_IZQUIERDA_MOVIMIENTO = "img/wizard/wizard_left_movimiento.gif";
        }else if(type.equals("priest")){
            refreshTime = 5;
            player = new Priest(name, type,4,5,1,65,"", 90,DEFAULT_LIFE,false);
            IMG_CARA_PARADO = "img/priest/priest_down_parado.png";
            IMG_DERECHA_PARADO = "img/priest/priest_right_parado.png";
            IMG_IZQUIERDA_PARADO = "img/priest/priest_left_parado.png";
            IMG_ESPALDA_PARADO = "img/priest/priest_up_parado.png";

            IMG_CARA_MOVIMIENTO = "img/priest/priest_down_movimiento.gif";
            IMG_ESPALDA_MOVIMIENTO = "img/priest/priest_up_movimiento.gif";
            IMG_DERECHA_MOVIMIENTO = "img/priest/priest_right_movimiento.gif";
            IMG_IZQUIERDA_MOVIMIENTO = "img/priest/priest_left_movimiento.gif";
        }

        lblPlayer.setToolTipText(type);
        lblPlayer.setPreferredSize(new Dimension(ANCHO_CHARACTERS,ALTO_CHARACTERS));
        lblPlayer.setSize(lblPlayer.getPreferredSize());
        icon = setIcon(IMG_DERECHA_PARADO,lblPlayer);
        lblPlayer.setIcon(icon);
        lblPlayer.setLocation(player.getCoordX(), player.getCoordY());



        healthPlayer.setPreferredSize(new Dimension(50,10));
        healthPlayer.setSize(healthPlayer.getPreferredSize());
        healthPlayer.setStringPainted(false);
        healthPlayer.setLocation(player.getCoordX(), player.getCoordY() - 10);
        healthPlayer.setBackground(Color.BLACK);
        healthPlayer.setForeground(Color.RED);
        healthPlayer.setMinimum(0);
        healthPlayer.setMaximum(DEFAULT_LIFE);
        healthPlayer.setValue(DEFAULT_LIFE);
        healthPlayer.setVisible(false);

        gamePanel.add(lblPlayer, 0);
        gamePanel.add(healthPlayer, 0);
    }

    private void setSkull(Skull skull, JLabel lblSkull, ArrayList<JLabel> skullsList){

        Random randomPoison = new Random();

        int value = randomPoison.nextInt((1 - 0) + 1) + 0;
        String poison = "#59377a";

        lblSkull.setSize(skull.getWidth(),skull.getHeight());
        icon = setIcon(skull.getImage(),lblSkull);
        lblSkull.setIcon(icon);
        lblSkull.setLocation(skull.getValueX(), skull.getValueY());
        lblSkull.setToolTipText(skull.getType());

        skull.getHealthBar().setPreferredSize(new Dimension(50,10));
        skull.getHealthBar().setSize(skull.getHealthBar().getPreferredSize());
        skull.getHealthBar().setStringPainted(false);
        skull.getHealthBar().setLocation(skull.getValueX(), skull.getValueY() - 10);
        skull.getHealthBar().setBackground(Color.BLACK);
        skull.getHealthBar().setForeground(Color.RED);
        if(value == 1){
            skull.getHealthBar().setForeground(Color.decode(poison));
        }
        skull.getHealthBar().setMinimum(0);
        skull.getHealthBar().setMaximum(skull.getLifePoints());
        skull.getHealthBar().setValue(skull.getLifePoints());

        skullsList.add(lblSkull);

        gamePanel.add(lblSkull, 1);
        gamePanel.add(skull.getHealthBar(),1);
    }

    private void relocatePlayer(Icon icon) {

        player.setCoordX(1);
        player.setCoordY(65);

        lblPlayer.setIcon(icon);
        lblPlayer.setLocation(player.getCoordX(),player.getCoordY());
        healthPlayer.setLocation(player.getCoordX(), player.getCoordY() - 10);
    }

    private void saveRanking(String mensajeEstadisticas, String ranking){

        Path path = Paths.get(ranking);

        if(Files.exists(path)){
            try {
                Files.writeString(path, mensajeEstadisticas + "\n", StandardOpenOption.APPEND);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            try {
                Files.writeString(path, mensajeEstadisticas + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int getDuration(Date horaInicio){

        int duration = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Date d1 = null;
        d1 = horaInicio;

        Date d2 = null;
        d2 = new Date();

        duration = (int) ((d2.getTime() - d1.getTime()) / 1000);

        return duration;
    }

    private boolean updateLifePoints(boolean poisoned) {

        if(!poisoned){
            poisoned = true;
            player.setLifePoints(player.getLifePoints() - 100);
            healthPlayer.setValue(player.getLifePoints());
            healthPlayer.validate();
            if(player.getLifePoints() <= 0){
                player.setPoisoned(false);
                lblPoisoned.setText("");
                checkDead(lblPlayer,null, false);
            }
            return poisoned;
        }
        return poisoned;
    }

    private void decreaseLife(){

        if(player.isPoisoned()){
            int delay = getDuration(horaInicio);
            if(delay % refreshTime == 0){
                poisoned = updateLifePoints(poisoned);
            }else{
                poisoned = false;
            }
        }
    }

    private void checkTime(){

        if(!timeChecked){
            if(seconds == 0){
                timeChecked = true;
                timeChecker.stop();
                checkDead(lblPlayer,null, false);
            }
        }
    }

    private class TimerActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            seconds--;
            lblTimeCounter.setText(seconds+ " sec");
        }
    }
}